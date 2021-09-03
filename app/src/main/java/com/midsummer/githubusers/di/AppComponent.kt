package com.midsummer.githubusers.di

import com.midsummer.githubusers.repository.UserDetailRepository
import com.midsummer.githubusers.repository.UserListRepository
import com.midsummer.githubusers.viewModel.UserDetailViewModel
import com.midsummer.githubusers.viewModel.UserListViewModel
import com.midsummer.githubusers.views.ui.userDetail.UserDetailActivity
import com.midsummer.githubusers.views.ui.userList.UserListActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by nienle on 03,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(userListRepository: UserListRepository)
    fun inject(userDetailRepository: UserDetailRepository)
    fun inject(userListViewModel: UserListViewModel)
    fun inject(userDetailViewModel: UserDetailViewModel)
    fun inject(userListActivity: UserListActivity)
    fun inject(userDetailActivity: UserDetailActivity)
}