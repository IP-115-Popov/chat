package com.eltex.data.repository

import android.util.Log
import com.eltex.data.api.HistoryChatApi
import com.eltex.data.mappers.MessageDTOToMessageMapper
import com.eltex.data.models.hitorymessge.HistoryMsgResponse
import com.eltex.domain.models.Message
import com.eltex.domain.repository.MessageHistoryRemoteRepository
import retrofit2.Response
import javax.inject.Inject

class MessageHistoryRemoteRepositoryImpl @Inject constructor(
    private val historyChatApi: HistoryChatApi
) : MessageHistoryRemoteRepository {
    override suspend fun getMessageHistory(
        roomId: String, roomType: String, offset: Int, count: Int
    ): List<Message> {
        try {
            val response: Response<HistoryMsgResponse>? = when (roomType) {
                "c" -> historyChatApi.getChannelsHistory(
                    roomId = roomId, offset = offset, count = count
                )

                "d" -> historyChatApi.getImHistory(roomId = roomId, offset = offset, count = count)
                "p" -> historyChatApi.getChannelsHistory(
                    roomId = roomId, offset = offset, count = count
                )

                else -> {
                    Log.e("Network", "raw channel format")
                    null
                }
            }

            val resMessage = response?.body()?.let { historyMsgResponse ->
                historyMsgResponse.messages.map { msg ->
                    MessageDTOToMessageMapper.map(msg)
                } ?: emptyList<Message>()
            }

            if (resMessage != null) {
                return resMessage
            } else {
                return emptyList<Message>()
            }

        } catch (e: Exception) {
            Log.e("Network", "MessageHistoryRepositoryImpl ${e.message}")
            e.printStackTrace()
            return emptyList<Message>()
        }
    }
}