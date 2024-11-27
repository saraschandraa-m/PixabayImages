package com.saraschandraa.pixabayusers.util

import android.content.Context
import androidx.preference.PreferenceManager

class PrefManagerHelper(context: Context) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getString(key: String): String {
        return prefs.getString(key, "").toString()
    }

    fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return prefs.getBoolean(key, false)
    }
}