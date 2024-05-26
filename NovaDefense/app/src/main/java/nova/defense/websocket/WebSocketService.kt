package nova.defense.websocket

import android.util.Log
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive

class WebSocketService(
    private val client: HttpClient
): SocketService {

    private var socket: WebSocketSession? = null

    override suspend fun initSession(): Resource<Unit> {
        return try {
            socket = client.webSocketSession {
                url(SocketService.Endpoints.ChatSocket.url)
            }
            if(socket?.isActive == true) {
                Resource.Success(Unit)
            } else Resource.Error("Couldn't establish a connection.")
        } catch(e: Exception) {
            e.localizedMessage?.let { Log.e("WebSocketService", it) }
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun sendMessage(message: String) {
        try {
            socket?.send(Frame.Text(message))
            Log.d("WebSocketService", message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeMessages(): Flow<String> {
        return try {
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    (it as? Frame.Text)?.readText() ?: ""
                } ?: flow {  }
        } catch(e: Exception) {
            e.localizedMessage?.let { Log.e("WebSocketService", it) }
            flow {  }
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}