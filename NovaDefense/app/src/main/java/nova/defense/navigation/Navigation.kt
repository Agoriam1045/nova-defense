package nova.defense.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nova.defense.screens.*
import nova.defense.screens.SignUpScreen

@Composable

fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screens.StartScreen.route) {
        composable(Screens.StartScreen.route) {
            StartScreen(navController)
        }
        composable(Screens.LogInScreen.route) {
            LogInScreen(navController)
        }
        composable(Screens.SignUpScreen.route) {
            SignUpScreen(navController)
        }
        composable(Screens.DeviceSelectScreen.route) {
            DeviceSelectScreen(navController = navController, text = "No Device") {

            }
        }
        composable(Screens.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }
        composable(Screens.ControlScreen.route) {
            ControlScreen(navController = navController)
        }
    }
}