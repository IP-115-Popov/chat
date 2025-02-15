package com.eltex.domain.repository

interface ChatCreationRemoteRepository {
    suspend fun createChat(
        xAuthToken: String, userId: String, userName: String
    )
}