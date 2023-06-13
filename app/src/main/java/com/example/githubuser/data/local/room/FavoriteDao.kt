package com.example.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser.data.local.entity.FavoriteUser
@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favoriteUser where favoriteUser.username = :username")
    fun getFavoriteUserbyUsername(username: String): LiveData<FavoriteUser>

    @Query("SELECT * FROM favoriteUser where isFavorite = 1")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Insert
    fun insertFavoriteUser(favoriteUser: FavoriteUser)

    @Query("DELETE FROM favoriteUser where favoriteUser.username = :username")
    fun deleteFavoriteUser(username: String)

}