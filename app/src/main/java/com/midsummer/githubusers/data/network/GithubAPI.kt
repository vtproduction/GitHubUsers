package com.midsummer.githubusers.data.network

import com.midsummer.githubusers.model.GithubUser
import com.midsummer.githubusers.model.GithubUserDetail
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by nienle on 03,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
interface GithubAPI {

    @GET("users")
    fun getUserList(@Query("since") since: Int,
                   @Query("per_page") itemPerPage: Int) : Flowable<List<GithubUser>>

    @GET("users/{userName}")
    fun getUserDetail(@Path("userName") userName: String) : Flowable<GithubUserDetail>
}