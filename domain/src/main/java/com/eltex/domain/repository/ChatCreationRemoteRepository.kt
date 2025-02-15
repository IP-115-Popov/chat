package com.eltex.domain.repository

interface ChatCreationRemoteRepository {
    suspend fun createChat(
       userName: String
    )
}