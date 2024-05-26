package nova.defense.navigation

sealed class Screens(val route: String) {
    object StartScreen : Screens("start_screen")
    object LogInScreen : Screens("log_in_screen")
    object SignUpScreen : Screens("sign_up_screen")
    object DeviceSelectScreen : Screens("device_select_screen")
    object SettingsScreen : Screens("settings_screen")
    object ControlScreen : Screens("control_screen")
}