package com.eltex.chat.data.storage


import android.content.Context
import com.eltex.chat.feature.authorization.models.AuthData
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesAuthDataStorage @Inject constructor(
    @ApplicationContext private val context: Context
) : AuthDataStorage {
    private val sharedPreferences = context.getSharedPreferences("my_app", Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun saveAuthData(authData: AuthData) {
        val userJson = gson.toJson(authData)
        sharedPreferences.edit().putString("user", userJson).apply()
    }

    override fun getAuthData(): AuthData? {
        val userJson = sharedPreferences.getString("user", null)
        return userJson?.let {
            try {
                gson.fromJson(it, AuthData::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}