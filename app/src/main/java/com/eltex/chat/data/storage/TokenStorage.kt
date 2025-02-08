package com.eltex.chat.data.storage

interface TokenStorage {
    fun saveToken(token: String)
    fun getToken(): String?
}