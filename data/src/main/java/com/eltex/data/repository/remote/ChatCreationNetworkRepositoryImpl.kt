package com.eltex.data.repository.remote

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.api.ChatCreationApi
import com.eltex.data.models.createchat.CreateChatRequest
import com.eltex.data.models.createchat.CreatedChatResponse
import com.eltex.domain.models.ChatModel
import com.eltex.domain.models.DataError
import com.eltex.domain.repository.remote.ChatCreationRemoteRepository
import retrofit2.Response
import javax.inject.Inject

class ChatCreationNetworkRepositoryImpl @Inject constructor(
    private val chatCreationApi: ChatCreationApi
) : ChatCreationRemoteRepository {
    override suspend fun createChat(userName: String): Either<DataError, ChatModel> {
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
                    return ChatModel(
                        id = createdChatResponse.room.rid,
                        name = userName,
                        t = createdChatResponse.room.t,
                        usernames = createdChatResponse.room.usernames,
                    ).right()
                } else {
                    Log.e("CreateChat", "Response body is null, but request was successful")
                }
            } else {
                Log.e(
                    "CreateChat", "Failed to create chat: ${response.code()} ${response.message()}"
                )
            }
            return when(response.code()) {
                in 400 until 500 -> DataError.IncorrectData.left()
                else ->  DataError.DefaultError.left()
            }

        } catch (e: Exception) {
            Log.e("CreateChat", "Exception during chat creation: ${e.message}", e)
            return DataError.ConnectionMissing.left()
        }
    }
}