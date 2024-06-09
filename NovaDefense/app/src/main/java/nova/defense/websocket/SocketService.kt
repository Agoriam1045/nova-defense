package nova.defense.websocket

import kotlinx.coroutines.flow.Flow

interface SocketService {

    suspend fun initSession(): Resource<Unit>

    suspend fun sendMessage(message: String)

    fun observeMessages(): Flow<String>

    suspend fun closeSession()

    companion object {
        const val BASE_URL = "ws://192.168.43.168:1045"
    }

    sealed class Endpoints(val url: String) {
        data object ChatSocket: Endpoints("$BASE_URL/nova")
    }
}