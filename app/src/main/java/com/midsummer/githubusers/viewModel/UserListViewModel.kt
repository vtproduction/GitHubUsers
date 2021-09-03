package com.midsummer.githubusers.viewModel

import androidx.lifecycle.ViewModel
import com.midsummer.githubusers.di.AppModule
import com.midsummer.githubusers.di.DaggerAppComponent
import com.midsummer.githubusers.repository.UserListRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by nienle on 03,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class UserListViewModel : ViewModel() {

    @Inject lateinit var repository: UserListRepository

    private val compositeDisposable by lazy { CompositeDisposable() }

    init {
        DaggerAppComponent
            .builder()
            .appModule(AppModule())
            .build().inject(this)

        compositeDisposable.add(repository.fetchDataFromDatabase())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}