package com.eltex.data.websockets

import android.util.Log
import com.eltex.domain.Сonstants
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit

class RocketChatWebSocket(
    private val listener: WebSocketListener,
) {

    var client: OkHttpClient? = null
    var webSocket: WebSocket? = null
    var request: Request? = null

    fun connect() {
        client = OkHttpClient.Builder()
            .readTimeout(4, TimeUnit.SECONDS)
            .writeTimeout(4, TimeUnit.SECONDS)
            .connectTimeout(4, TimeUnit.SECONDS)
            .pingInterval(10, TimeUnit.SECONDS)
            .build()

        request = Request.Builder()
            .url(Сonstants.BASE_WSS)
            .build()

        try {
            webSocket = client?.newWebSocket(request!!, listener)
            if (webSocket == null) {
                Log.e("WebSocket", "WebSocket is null after creation!")
            } else {
                Log.i("WebSocket", "WebSocket created successfully.")
            }
        } catch (e: Exception) {
            Log.e("WebSocket", "Error creating WebSocket: ${e.message}", e)
        }
    }


    fun sendMessage(message: String) {
        Log.i("WebSocket", "sendMessage $message")
        webSocket?.send(message)
    }

    fun close() {
        webSocket?.close(1000, "Normal Closure")
        client?.dispatcher?.executorService?.shutdown()
        client = null
        webSocket = null
        request = null
    }
}
