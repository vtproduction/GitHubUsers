package com.midsummer.githubusers.model

import com.squareup.moshi.Json

/**
 * Created by nienle on 02,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
data class GithubUserDetail(
    val id: Int,

    @Json(name="login")
    val username: String,

    @Json(name="avatar_url")
    val avatarUrl: String,

    val location: String?,
    val bio: String?,
    val followers: Int,
    val following: Int,
    val publicRepo: Int,
)
