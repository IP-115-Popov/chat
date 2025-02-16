package com.eltex.domain.repository.remote

interface ChatCreationRemoteRepository {
    suspend fun createChat(
        userName: String
    )
}