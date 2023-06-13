package com.example.githubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteUser")
data class FavoriteUser(
    @PrimaryKey(autoGenerate = false)
    @field:ColumnInfo(name = "username")
    var userName: String,

    @field:ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null,

    @field:ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
)
