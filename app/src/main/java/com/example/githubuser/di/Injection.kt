package com.example.githubuser.di

import android.content.Context
import com.example.githubuser.data.GithubRepository
import com.example.githubuser.data.local.room.FavoriteDatabase
import com.example.githubuser.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context) : GithubRepository{
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        return GithubRepository.getInstance(dao, appExecutors)
    }
}