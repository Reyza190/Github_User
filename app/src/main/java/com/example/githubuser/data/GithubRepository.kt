package com.example.githubuser.data

import androidx.lifecycle.LiveData


import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.data.local.room.FavoriteDao
import com.example.githubuser.utils.AppExecutors

class GithubRepository(
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
) {

    fun setFavoriteUser(favoriteUser: FavoriteUser, favoriteMark: Boolean) {
        appExecutors.diskIO.execute {
            favoriteUser.isFavorite = favoriteMark
            favoriteDao.insertFavoriteUser(favoriteUser)
        }
    }

    fun deleteFavoriteUser(userName: String){
        appExecutors.diskIO.execute{
            favoriteDao.deleteFavoriteUser(userName)
        }
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>{
        return favoriteDao.getFavoriteUser()
    }

    fun getFavoriteUserbyUsername(userName: String): LiveData<FavoriteUser> {
        return favoriteDao.getFavoriteUserbyUsername(userName)
    }


    companion object {
        @Volatile
        private var instance: GithubRepository? = null
        fun getInstance(
            favoriteDao: FavoriteDao,
            appExecutors: AppExecutors
        ): GithubRepository =
            instance ?: synchronized(this) {
                instance ?: GithubRepository(favoriteDao, appExecutors)
            }.also { instance = it }
    }
}