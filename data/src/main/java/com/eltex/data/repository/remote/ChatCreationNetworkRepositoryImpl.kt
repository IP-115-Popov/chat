package com.eltex.data.repository.remote

import android.util.Log
import com.eltex.data.api.ChatCreationApi
import com.eltex.data.models.createchat.CreateChatRequest
import com.eltex.data.models.createchat.CreatedChatResponse
import com.eltex.domain.repository.remote.ChatCreationRemoteRepository
import retrofit2.Response
import javax.inject.Inject

class ChatCreationNetworkRepositoryImpl @Inject constructor(
    private val chatCreationApi: ChatCreationApi
) : ChatCreationRemoteRepository {
    override suspend fun createChat(userName: String) {
        val response: Response<CreatedChatResponse>
        try {
            response = chatCreationApi.createChat(
                createChatRequest = CreateChatRequest(
                    username = userName
                ),
            )

            if (response.isSuccessful) {
                val createdChatResponse = response.body()
                if (createdChatResponse != null) {
                    Log.d("CreateChat", "Chat created successfully: $createdChatResponse")
                } else {
                    Log.e("CreateChat", "Response body is null, but request was successful")
                }
            } else {
                Log.e(
                    "CreateChat", "Failed to create chat: ${response.code()} ${response.message()}"
                )
            }

        } catch (e: Exception) {
            Log.e("CreateChat", "Exception during chat creation: ${e.message}", e)
        }
    }
}