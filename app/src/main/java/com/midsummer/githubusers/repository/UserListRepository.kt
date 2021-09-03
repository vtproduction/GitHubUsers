package com.midsummer.githubusers.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.midsummer.githubusers.GithubUsersApplication
import com.midsummer.githubusers.data.local.ApplicationDatabase
import com.midsummer.githubusers.data.local.toUserEntityList
import com.midsummer.githubusers.data.local.toUserList
import com.midsummer.githubusers.data.network.GithubAPI
import com.midsummer.githubusers.di.AppModule
import com.midsummer.githubusers.di.DaggerAppComponent
import com.midsummer.githubusers.internal.RESULT_PER_PAGE
import com.midsummer.githubusers.internal.START_QUERY_AT
import com.midsummer.githubusers.model.GithubUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

/**
 * Created by nienle on 03,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class UserListRepository {

    @Inject lateinit var api: GithubAPI

    private val _data by lazy { MutableLiveData<List<GithubUser>>() }
    val data: LiveData<List<GithubUser>>
        get() = _data

    private val _isInProgress by lazy { MutableLiveData<Boolean>() }
    val isInProgress: LiveData<Boolean>
        get() = _isInProgress

    private val _isError by lazy { MutableLiveData<Boolean>() }
    val isError: LiveData<Boolean>
        get() = _isError



    init {
        DaggerAppComponent
            .builder()
            .appModule(AppModule())
            .build().inject(this)
    }

    private fun subscribeToDatabase(): DisposableSubscriber<List<GithubUser>> {
        return object : DisposableSubscriber<List<GithubUser>>() {

            override fun onNext(result: List<GithubUser>?) {
                if (result != null) {
                    val entityList = result.toUserEntityList()
                    Log.d("UserListLOG", "subscribeToDatabase: ${entityList.size}")
                    GithubUsersApplication.database.apply {
                        userDAO().insertData(entityList)
                    }
                }
            }

            override fun onError(t: Throwable?) {
                _isInProgress.postValue(true)
                Log.e("insertData()", "error: ${t?.message}")
                _isError.postValue(true)
                _isInProgress.postValue(false)
            }

            override fun onComplete() {
                Log.v("insertData()", "insert success")
                getUserListQuery()
            }
        }
    }

    private fun getUserListQuery(): Disposable {
        return GithubUsersApplication.database.userDAO()
            .queryData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { dataEntityList ->
                    Log.d("UserListLOG", "getUserListQuery: ${dataEntityList.size}")
                    _isInProgress.postValue(true)
                    if (dataEntityList.isNotEmpty()) {
                        _isError.postValue(false)
                        _data.postValue(dataEntityList.toUserList())
                    } else {
                        insertData()
                    }
                    _isInProgress.postValue(false)

                },
                {
                    _isInProgress.postValue(true)
                    Log.e("getArticleListQuery()", "Database error: ${it.message}")
                    _isError.postValue(true)
                    _isInProgress.postValue(false)
                }
            )
    }

    private fun insertData(): Disposable {
        return api.getUserList(START_QUERY_AT, RESULT_PER_PAGE)
            .subscribeOn(Schedulers.io())
            .subscribeWith(subscribeToDatabase())
    }

    fun fetchDataFromDatabase(): Disposable = getUserListQuery()
}