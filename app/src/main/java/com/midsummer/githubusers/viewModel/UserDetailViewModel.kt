package com.midsummer.githubusers.viewModel

import androidx.lifecycle.ViewModel
import com.midsummer.githubusers.di.AppModule
import com.midsummer.githubusers.di.DaggerAppComponent
import com.midsummer.githubusers.repository.UserDetailRepository
import com.midsummer.githubusers.repository.UserListRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by nienle on 03,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class UserDetailViewModel : ViewModel() {

    @Inject lateinit var repository: UserDetailRepository

    private val compositeDisposable by lazy { CompositeDisposable() }

    init {
        DaggerAppComponent
            .builder()
            .appModule(AppModule())
            .build().inject(this)


    }

    fun fetchData(userName: String){
        repository.setupUserName(userName)
        compositeDisposable.add(repository.fetchDataFromDatabase())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}