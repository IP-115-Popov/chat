package com.eltex.chat.data.storage


import android.content.Context
import com.eltex.chat.models.User
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesTokenStorage @Inject constructor(
    @ApplicationContext private val context: Context
) : UserStorage {
    private val sharedPreferences = context.getSharedPreferences("my_app", Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        sharedPreferences.edit().putString("user", userJson).apply()
    }

    override fun getUser(): User? {
        val userJson = sharedPreferences.getString("user", null)
        return userJson?.let {
            try {
                gson.fromJson(it, User::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}