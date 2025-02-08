package com.eltex.chat.data.storage


import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesTokenStorage @Inject constructor(
    @ApplicationContext private val context: Context
) : TokenStorage {
    private val sharedPreferences = context.getSharedPreferences("my_app", Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }
}