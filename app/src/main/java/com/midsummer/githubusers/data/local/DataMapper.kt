package com.midsummer.githubusers.data.local

import com.midsummer.githubusers.model.GithubUser
import com.midsummer.githubusers.model.GithubUserDetail

/**
 * Created by nienle on 02,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */


fun UserEntity.toUser() = GithubUser(
    this.id,
    this.username,
    this.avatarUrl
)

fun List<UserEntity>.toUserList() = this.map { it.toUser() }

fun GithubUser.toUserEntity() = UserEntity(
    this.id,
    this.username,
    this.avatarUrl
)

fun List<GithubUser>.toUserEntityList() = this.map { it.toUserEntity() }

fun UserDetailEntity.toUserDetail() = GithubUserDetail(
    this.id,
    this.username,
    this.avatarUrl,
    this.location,
    this.bio,
    this.followers,
    this.following,
    this.publicRepo
)

fun GithubUserDetail.toUserDetailEntity() = UserDetailEntity(
    this.id,
    this.username,
    this.avatarUrl,
    this.location,
    this.bio,
    this.followers,
    this.following,
    this.publicRepo
)