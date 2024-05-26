package nova.defense.controls

import androidx.lifecycle.ViewModel
import nova.defense.websocket.WebSocketClient

class ControlViewModel : ViewModel() {
    suspend fun sendJoystickPosition(x: Float, y: Float) {
        WebSocketClient.service.sendMessage("{\"x\" : \"${x}\", \"y\" : \"${y}\"}")
    }

    suspend fun sendButtonClicked() {
        WebSocketClient.service.sendMessage("SHOOT_COMMAND")
    }
}
