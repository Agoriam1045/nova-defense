package nova.defense.screens

import android.util.Log
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ControlCamera
import androidx.compose.material.icons.filled.ControlPoint
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import nova.defense.joystick.JoyStick
import nova.defense.R
import nova.defense.camera.CameraPreview
import nova.defense.controls.ControlViewModel
import nova.defense.navigation.BottomNavBar
import nova.defense.navigation.NavBarItem
import nova.defense.navigation.Screens
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import nova.defense.websocket.WebSocketClient

@Composable
fun ControlScreen(
    navController: NavHostController,
    viewModel: ControlViewModel = viewModel()
) {
    val applicationContext = LocalContext.current.applicationContext
    val camController = remember {
        LifecycleCameraController(applicationContext).apply {

        }
    }

    LaunchedEffect(true) {
        WebSocketClient.service.initSession();
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    Box(modifier = Modifier.fillMaxSize()    ) {
        CameraPreview(
            controller = camController,
            modifier = Modifier.fillMaxSize(),
        )
    }

    Column(
        modifier = Modifier.height(5.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Spacer(Modifier.height(475.dp))
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            JoyStick(
                Modifier.padding(30.dp),
                size = 150.dp,
                dotSize = 30.dp
            ){ x: Float, y: Float ->
                lifecycleOwner.lifecycleScope.launch {
                    viewModel.sendJoystickPosition(x, y);
                }
            }
            ElevatedButton(
                modifier = Modifier
                    .padding(40.dp)
                    .size(40.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Cyan,
                        shape = CircleShape
                    ),

                shape = CircleShape,
                onClick = {
                    lifecycleOwner.lifecycleScope.launch {
                        viewModel.sendButtonClicked();
                    }
                },

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    Icons.Rounded.Bolt,
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = "shoot_button"
                )
            }
        }

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(40.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.crosshair),
                contentDescription = "crosshair",
                contentScale = ContentScale.Inside,
                modifier = Modifier.matchParentSize()
            )
        }
    }

    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        BottomNavBar(
            items = listOf(
                NavBarItem(
                    name = "Devices",
                    route = Screens.DeviceSelectScreen.route,
                    icon = Icons.Default.ControlPoint
                ),
                NavBarItem(
                    name = "Control",
                    route = Screens.ControlScreen.route,
                    icon = Icons.Default.ControlCamera
                ),
                NavBarItem(
                    name = "Options",
                    route = Screens.SettingsScreen.route,
                    icon = Icons.Default.AccountCircle
                )
            ),
            navController = navController,
            onItemClick = {
                navController.navigate(it.route)
            }
        )
    }
}