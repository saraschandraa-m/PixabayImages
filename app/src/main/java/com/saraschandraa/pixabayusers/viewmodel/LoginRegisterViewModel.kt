package com.saraschandraa.pixabayusers.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.saraschandraa.pixabayusers.database.AppDatabase
import com.saraschandraa.pixabayusers.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginRegisterViewModel(private val application: Application) :
    AndroidViewModel(application = application) {


    val emailPatternError by lazy { MutableLiveData<Boolean>() }
    val emailExistsError by lazy { MutableLiveData<Boolean>() }
    val processSuccess by lazy { MutableLiveData<Boolean>() }
    lateinit var roomDatabase: AppDatabase

    private fun initDB() {
        roomDatabase = Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::
            class.java,
            "pixbay_user_db"
        ).build()
    }


    fun register(user: User) {
        initDB()
        if (!Pattern.compile(".+@.+\\.[a-z]+").matcher(user.email).matches()) {
            emailPatternError.value = true
        } else {
            emailPatternError.value = false
            CoroutineScope(Dispatchers.IO).launch {
                if (!roomDatabase.userDao().checkEmailExists(user.email)) {
                    emailExistsError.postValue(false)
                    roomDatabase.userDao().insertQuery(user)
                    processSuccess.postValue(true)
                } else {
                    emailExistsError.postValue(true)
                    processSuccess.postValue(false)
                }
            }
        }
    }

    fun login(email: String, password: String) {
        initDB()
        if (!Pattern.compile(".+@.+\\.[a-z]+").matcher(email).matches()) {
            emailPatternError.value = true
        } else {
            emailPatternError.value = false
            CoroutineScope(Dispatchers.IO).launch {
                if (roomDatabase.userDao().checkEmailAndPassword(email, password)) {
                    processSuccess.postValue(true)
                } else {
                    if (!roomDatabase.userDao().checkEmailExists(email)) {
                        emailExistsError.postValue(true)
                    } else {
                        processSuccess.postValue(false)
                    }
                }
            }
        }
    }

}