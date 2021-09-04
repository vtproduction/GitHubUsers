package com.midsummer.githubusers.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.EmptyResultSetException
import com.midsummer.githubusers.GithubUsersApplication
import com.midsummer.githubusers.data.local.*
import com.midsummer.githubusers.data.network.GithubAPI
import com.midsummer.githubusers.di.AppModule
import com.midsummer.githubusers.di.DaggerAppComponent
import com.midsummer.githubusers.model.GithubUser
import com.midsummer.githubusers.model.GithubUserDetail
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
class UserDetailRepository{

    private var userName: String = ""

    fun setupUserName(userName: String){
        this.userName = userName
    }

    @Inject lateinit var api: GithubAPI

    private val _data by lazy { MutableLiveData<GithubUserDetail>() }
    val data: LiveData<GithubUserDetail>
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

    private fun subscribeToDatabase(): DisposableSubscriber<GithubUserDetail> {
        return object : DisposableSubscriber<GithubUserDetail>() {

            override fun onNext(result: GithubUserDetail?) {
                if (result != null) {
                    val entity = result.toUserDetailEntity()
                    Log.d("subscribeToDatabase", "subscribeToDatabase: $entity")
                    GithubUsersApplication.database.apply {
                        userDetailDAO().insertData(entity)
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
                getUserQuery()
            }
        }
    }

    private fun getUserQuery(): Disposable {
        return GithubUsersApplication.database.userDetailDAO()
            .queryData(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { dataEntity ->
                    Log.d("getUserQuery", "getUserQuery: $dataEntity")
                    _isInProgress.postValue(true)
                    if (dataEntity != null) {
                        _isError.postValue(false)
                        _data.postValue(dataEntity.toUserDetail())
                    } else {
                        insertData()
                    }
                    _isInProgress.postValue(false)

                },
                {
                    if(it is EmptyResultSetException){
                        insertData()
                    }else{
                        _isInProgress.postValue(true)
                        Log.e("getUserQuery()", "Database error: ${it.message}")
                        _isError.postValue(true)
                        _isInProgress.postValue(false)
                    }

                }
            )
    }

    private fun insertData(): Disposable {
        return api.getUserDetail(userName)
            .subscribeOn(Schedulers.io())
            .subscribeWith(subscribeToDatabase())
    }

    fun fetchDataFromDatabase(): Disposable = getUserQuery()
}