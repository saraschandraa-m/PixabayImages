package com.saraschandraa.pixabayusers.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userTable")
data class User(
    @PrimaryKey val email: String,
    val password: String?,
    val dob: String?,
    val name: String?
)
