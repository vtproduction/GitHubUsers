package com.midsummer.githubusers

import android.app.Application
import com.midsummer.githubusers.data.local.ApplicationDatabase

/**
 * Created by nienle on 02,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class GithubUsersApplication : Application() {

    companion object {
        lateinit var instance: GithubUsersApplication
        lateinit var database: ApplicationDatabase
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        database = ApplicationDatabase.invoke(this)

    }
}