package com.eltex.data.websockets

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONObject

class RocketChatWebSocketListener(val messageListener: (JSONObject) -> Unit) : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.i("WebSocket", "WebSocket connected  ${response.message}")
        //connect
        webSocket.send("""{"msg": "connect", "version": "1", "support": ["1"]}""")
    }


    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.i("WebSocket", "message from server - $text")
        try {
            val json = JSONObject(text)
            messageListener(json)
        } catch (e: Exception) {
            Log.e("WebSocket", "Error parsing JSON: ${e.message}")
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        println("Receiving bytes : ${bytes.hex()}")
    }


    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.i("WebSocket", "Closing: $code / $reason")
        webSocket.close(1000, null)
    }


    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e("WebSocket", "Error: ${t.message}")
        t.printStackTrace()
    }
}