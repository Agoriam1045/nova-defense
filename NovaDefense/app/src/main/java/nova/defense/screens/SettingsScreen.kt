package nova.defense.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ControlCamera
import androidx.compose.material.icons.filled.ControlPoint
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import nova.defense.navigation.BottomNavBar
import nova.defense.navigation.NavBarItem
import nova.defense.navigation.Screens

@Composable
fun SettingsScreen(
    navController: NavHostController,
) {
    Row {
        Spacer(modifier = Modifier.width(30.dp))
        Column {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "UserName",
                fontSize = 30.sp
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(onClick = { navController.navigate(Screens.LogInScreen.route) }) {
            Text(text = "Log Out")
        }
        Spacer(modifier = Modifier.height(60.dp))
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
