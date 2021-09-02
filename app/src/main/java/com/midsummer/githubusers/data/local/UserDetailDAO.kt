package com.midsummer.githubusers.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

/**
 * Created by nienle on 02,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
@Dao
interface UserDetailDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: UserDetailEntity)

    @Query("SELECT * from userDetail WHERE username = :username")
    fun queryData(username: String): Single<UserDetailEntity>
}