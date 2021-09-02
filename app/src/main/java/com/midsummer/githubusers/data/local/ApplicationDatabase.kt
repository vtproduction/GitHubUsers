package com.midsummer.githubusers.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.midsummer.githubusers.internal.DATABASE_NAME

/**
 * Created by nienle on 02,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */

@Database(entities = [UserEntity::class, UserDetailEntity::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase(){

    abstract fun userDAO(): UserDAO
    abstract fun userDetailDAO() : UserDetailDAO

    companion object {

        @Volatile
        private var instance: ApplicationDatabase? = null

        private val lock = Any()

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ApplicationDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: buildDatabase(context).also { instance = it }
        }
    }
}