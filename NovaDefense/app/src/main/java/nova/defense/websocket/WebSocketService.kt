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

    private var controlSocket: WebSocketSession? = null
    private var imageSocket: WebSocketSession? = null

    override suspend fun initSession(): Resource<Unit> {
        return try {
            controlSocket = client.webSocketSession {
                url("${SocketService.BASE_URL}/app")
            }
            imageSocket = client.webSocketSession {
                url("${SocketService.BASE_URL}/nova")
            }
            if(controlSocket?.isActive == true) {
                Resource.Success(Unit)
            } else Resource.Error("Couldn't establish a connection.")
        } catch(e: Exception) {
            e.localizedMessage?.let { Log.e("WebSocketService", it) }
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun sendMessage(message: String) {
        try {
            controlSocket?.send(Frame.Text(message))
            Log.d("WebSocketService", message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeMessages(): Flow<ByteArray> {
        return try {
            imageSocket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Binary }
                ?.map {
                    (it as? Frame.Binary)?.readBytes() ?: ByteArray(0)
                } ?: flow {  }
        } catch(e: Exception) {
            e.localizedMessage?.let { Log.e("WebSocketService", it) }
            flow {  }
        }
    }

    override suspend fun closeSession() {
        controlSocket?.close()
        imageSocket?.close()
    }
}