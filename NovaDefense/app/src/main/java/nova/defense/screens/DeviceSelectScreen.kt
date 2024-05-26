package nova.defense.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import nova.defense.navigation.BottomNavBar
import nova.defense.navigation.NavBarItem
import nova.defense.navigation.Screens

@Composable
fun DeviceSelectScreen(
    navController: NavHostController,
    text: String,
    modifier: Modifier = Modifier,
    initiallyOpened: Boolean = false,
    content: @Composable () -> Unit
) {
    var Device: String by remember { mutableStateOf(text) }
    var isOpen by remember {
        mutableStateOf(initiallyOpened)
    }
    val alpha: Float by animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    val rotateX: Float by animateFloatAsState(
        targetValue = if (isOpen) 0f else -90f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(150.dp))
        Text(
            text = "Choose the device",
            fontSize = 30.sp
            )
        Text(
            text = "you'd like to control:",
            fontSize = 30.sp
            )
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = Device,
                color = Color.White,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open or close drop down",
                tint = Color.White,
                modifier = Modifier
                    .clickable { isOpen = !isOpen }
                    .scale(1f, if (isOpen) -1f else 1f)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        transformOrigin = TransformOrigin(0.5f, 0f)
                        rotationX = rotateX
                    }
                    .alpha(alpha),
                shape = CutCornerShape(10),
                onClick = { Device = "No Device"}
            ) {
                Text(
                    text = "No Device",)
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        transformOrigin = TransformOrigin(0.5f, 0f)
                        rotationX = rotateX
                    }
                    .alpha(alpha),
                shape = CutCornerShape(10),
                onClick = { Device = "Nova Mk. II" }
            ) {
                Text(text = "Nova Mk. II", modifier = modifier.alpha(alpha))
            }
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

