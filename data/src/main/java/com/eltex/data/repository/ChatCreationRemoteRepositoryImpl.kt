package com.eltex.data.repository

import android.util.Log
import com.eltex.data.api.ChatCreationApi
import com.eltex.data.models.createchat.CreateChatRequest
import com.eltex.data.models.createchat.CreatedChatResponse
import com.eltex.domain.repository.ChatCreationRemoteRepository
import retrofit2.Response
import javax.inject.Inject

class ChatCreationRemoteRepositoryImpl @Inject constructor(
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
                // Запрос успешен
                val createdChatResponse = response.body()
                if (createdChatResponse != null) {
                    // Обработка успешного ответа
                    Log.d("CreateChat", "Chat created successfully: $createdChatResponse")
                    // ... дальнейшая обработка ...
                } else {
                    // Тело ответа null, хотя запрос успешен
                    Log.e("CreateChat", "Response body is null, but request was successful")
                    // Можно выбросить исключение или обработать как-то иначе
                }
            } else {
                // Запрос не успешен
                Log.e(
                    "CreateChat",
                    "Failed to create chat: ${response.code()} ${response.message()}"
                )
                // Можно выбросить исключение или обработать как-то иначе
                throw Exception("Failed to create chat: ${response.code()} ${response.message()}")
            }

        } catch (e: Exception) {
            // Обработка исключений
            Log.e("CreateChat", "Exception during chat creation: ${e.message}", e)
            throw e // Пробрасываем исключение, чтобы можно было обработать его выше
        }

    }
}