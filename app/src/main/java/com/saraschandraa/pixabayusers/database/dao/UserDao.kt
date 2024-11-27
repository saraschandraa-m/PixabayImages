package com.saraschandraa.pixabayusers.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saraschandraa.pixabayusers.model.User

@Dao
interface UserDao {

    @Insert
    fun insertQuery(user: User)


    @Query("SELECT EXISTS(SELECT * from userTable WHERE email = :email)")
    fun checkEmailExists(email: String): Boolean

    @Query("SELECT EXISTS(SELECT * from userTable WHERE email = :email and password = :password)")
    fun checkEmailAndPassword(email: String, password: String): Boolean
}