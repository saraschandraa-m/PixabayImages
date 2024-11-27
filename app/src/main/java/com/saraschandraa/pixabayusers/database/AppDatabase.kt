package com.saraschandraa.pixabayusers.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saraschandraa.pixabayusers.database.dao.UserDao
import com.saraschandraa.pixabayusers.model.User

@Database(version = 1, entities = [User::class])
abstract class AppDatabase : RoomDatabase() {


    private var INSTANCE: AppDatabase? = null


    fun getMemoryInDatabase(context: Context): AppDatabase {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "pixbay_user_db")
                .fallbackToDestructiveMigration().build()
        }
        return INSTANCE as AppDatabase
    }


    abstract fun userDao(): UserDao

}