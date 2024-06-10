package nova.defense.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nova.defense.websocket.WebSocketClient

@Composable
fun WebSocketImageDisplay() {
    val listener = remember { WebSocketClient }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(Unit) {
        launch {
            while (true) {
                delay(40)
                listener.service.observeMessages().collect { byteArray ->
                    Log.d("WS", "Image received")
                    val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    bitmap = bmp
                }
            }
        }
    }

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
    }
}