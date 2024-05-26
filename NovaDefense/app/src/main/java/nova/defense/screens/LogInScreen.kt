package nova.defense.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import nova.defense.navigation.Screens

@Composable
fun LogInScreen(
    navController: NavHostController,
    ) {

    var userMail by remember { mutableStateOf(value = "") }
    var password by remember { mutableStateOf(value = "") }
    var showPassword by remember { mutableStateOf(value = false) }

    Row {
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Spacer(modifier = Modifier.height(30.dp))
            Button(

                onClick = { navController.navigate(Screens.StartScreen.route) },
                shape = CircleShape,
                modifier = Modifier.size(40.dp),
                contentPadding = PaddingValues(0.dp)
            ) {

                Icon(
                    Icons.Rounded.ArrowBack,
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = "back_button"
                )
            }
        }
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Log In:",
            modifier = Modifier,
            fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(50.dp))
        TextField(
            value = userMail,
            label = { Text("Username/E-mail") },
            onValueChange = {userMail = it},
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = password,
            label = { Text("Password") },
            onValueChange = {password = it},
            singleLine = true,
            visualTransformation =  if (showPassword) {

                VisualTransformation.None

            } else {

                PasswordVisualTransformation()

            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "hide_password"
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showPassword = true }) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "hide_password"
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(90.dp))
        Button(
            onClick = { navController.navigate(Screens.DeviceSelectScreen.route) }
        ) {
            Text("Advance")
        }
    }
}