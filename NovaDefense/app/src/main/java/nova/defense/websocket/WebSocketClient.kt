package nova.defense.websocket

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*

object WebSocketClient {
    private val client = HttpClient(CIO) {
        install(WebSockets){
            pingInterval = 20_000
        }
    }

   val service = WebSocketService(client)
}