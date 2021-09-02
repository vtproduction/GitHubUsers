package com.midsummer.githubusers.model

import com.squareup.moshi.Json

/**
 * Created by nienle on 02,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
data class GithubUser(
    val id: Int,

    @Json(name="login")
    val username: String,

    @Json(name="avatar_url")
    val avatarUrl: String

)
