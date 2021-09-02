package com.midsummer.githubusers.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Created by nienle on 02,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
@Entity(tableName = "userDetail")
data class UserDetailEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="id")
    val id: Int,

    @ColumnInfo(name="username")
    val username: String,

    @ColumnInfo(name="avatarUrl")
    val avatarUrl: String,

    @ColumnInfo(name="location")
    val location: String?,

    @ColumnInfo(name="bio")
    val bio: String?,

    @ColumnInfo(name="followers")
    val followers: Int,

    @ColumnInfo(name="following")
    val following: Int,

    @ColumnInfo(name="publicRepo")
    val publicRepo: Int,
)
