package com.eltex.domain.repository

interface ChatCreationNetworkRepository {
    suspend fun createChat(
        xAuthToken: String, userId: String, userName: String
    )
}