package com.eltex.data.repository.remote

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.api.MembersApi
import com.eltex.domain.models.ChatModel
import com.eltex.domain.models.ChatsMember
import com.eltex.domain.models.DataError
import com.eltex.domain.repository.remote.ChatMembersRemoteRepository
import javax.inject.Inject

class ChatMembersRemoteRepositoryImpl @Inject constructor(
    private val membersApi: MembersApi
) : ChatMembersRemoteRepository {
    override suspend fun get(chatModel: ChatModel): Either<DataError, List<ChatsMember>> {
        try {
            val response = when (chatModel.t) {
                "c" -> {
                    membersApi.getChannelMembers(chatModel.id)
                }

                "p" -> {
                    membersApi.getGroupMembers(chatModel.id)
                }

                else -> {
                    return DataError.IncorrectData.left()
                }
            }
            if (response.isSuccessful) {
                response.body()?.members?.map {
                    ChatsMember(
                        id = it._id,
                        name = it.name,
                        status = it.status,
                        username = it.username,
                    )
                }?.let { members ->
                    return members.right()
                } ?: return DataError.DefaultError.left()
            } else {
                return when (response.code()) {
                    in 400 until 500 -> DataError.IncorrectData.left()
                    else -> DataError.DefaultError.left()
                }
            }
        } catch (e: Exception) {
            Log.e("ChatMembersRemoteRepository", "get ${e.message}")
            e.printStackTrace()
            return DataError.DefaultError.left()
        }
    }
}