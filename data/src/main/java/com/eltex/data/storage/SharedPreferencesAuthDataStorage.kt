package com.eltex.data.storage


import android.content.Context
import com.eltex.data.models.AuthDataEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SharedPreferencesAuthDataStorage @Inject constructor(
    @ApplicationContext private val context: Context
) : AuthDataStorage {
    private val sharedPreferences = context.getSharedPreferences("my_app", Context.MODE_PRIVATE)


    override fun saveAuthData(authData: AuthDataEntity) {
        val userJson = Json.encodeToString(authData)
        sharedPreferences.edit().putString("user", userJson).apply()
    }

    override fun getAuthData(): AuthDataEntity? {
        val userJson = sharedPreferences.getString("user", null)
        return userJson?.let {
            try {
                Json.decodeFromString<AuthDataEntity>(userJson)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}