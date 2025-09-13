package com.simats.app

import android.content.Context
import android.content.SharedPreferences

object UserSession {
    private const val PREF_NAME = "TruckRentalSession"
    private const val KEY_USERNAME = "username"
    private const val KEY_PHONE = "phoneno"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUser(context: Context, username: String, phoneno: String) {
        val editor = getPrefs(context).edit()
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_PHONE, phoneno)
        editor.apply()
    }

    fun getUsername(context: Context): String? {
        return getPrefs(context).getString(KEY_USERNAME, null)
    }

    fun getPhone(context: Context): String? {
        return getPrefs(context).getString(KEY_PHONE, null)
    }

    fun clear(context: Context) {
        getPrefs(context).edit().clear().apply()
    }
}
