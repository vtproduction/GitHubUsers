package com.midsummer.githubusers.di

import com.midsummer.githubusers.data.network.GithubAPI
import com.midsummer.githubusers.data.network.GithubAPIService
import com.midsummer.githubusers.model.GithubUser
import com.midsummer.githubusers.repository.UserDetailRepository
import com.midsummer.githubusers.repository.UserListRepository
import com.midsummer.githubusers.views.adapter.UserListAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by nienle on 03,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideApi(): GithubAPI = GithubAPIService.getClient()

    @Provides
    fun provideUserListRepository() = UserListRepository()

    @Provides
    fun provideUserDetailRepository() = UserDetailRepository()

    @Provides
    fun provideListData() = ArrayList<GithubUser>()

    @Provides
    fun provideUserListAdapter(data: ArrayList<GithubUser>):
            UserListAdapter = UserListAdapter(data)
}