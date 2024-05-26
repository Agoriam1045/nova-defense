package nova.defense.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import nova.defense.navigation.Screens
import nova.defense.validate.password.SignUpViewModel

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = viewModel()
) {

    var username by remember { mutableStateOf("") }
    var eMail by remember { mutableStateOf(value = "") }
    var password by remember { mutableStateOf(value = "") }
    var showPassword by remember { mutableStateOf(value = false) }
    val passwordError by viewModel.passwordError.collectAsStateWithLifecycle()


    Row(
        modifier = Modifier
    ) {
        Spacer(
            modifier = Modifier.width(20.dp)
        )
        Column(
            modifier = Modifier
        ) {
            Spacer(
                modifier = Modifier.height(30.dp)
            )
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
            text = "Join Nova:",
            modifier = Modifier,
            fontSize = 40.sp
        )

        Spacer(modifier = Modifier.height(50.dp))

        TextField(
            value = eMail,
            label = { Text("E-mail") },
            onValueChange = {eMail = it},
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = username,
            label = { Text("Username") },
            onValueChange = {username = it},
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = viewModel.password,
            label = { Text("Password") },
            onValueChange = viewModel:: changePassword,
            singleLine = true,
            visualTransformation =  if (showPassword) {

                VisualTransformation.None

            } else {

                PasswordVisualTransformation()

            },
            isError = !passwordError.successful,
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

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            ConditionRow(condition = "Minimum 6 characters", check = passwordError.hasMin)
            ConditionRow(condition = "Minimum 1 uppercase letter", check = passwordError.hasUpper)
            ConditionRow(condition = "Minimum 1 special character", check = passwordError.hasSpecial)
        }

        Button(
            onClick = { navController.navigate(Screens.LogInScreen.route) }
        ) {
            Text("Advance")
        }
    }
}

@Composable
fun ConditionRow(
    condition: String,
    check: Boolean
) {
    val color by animateColorAsState(
        if (check) Color.Green else Color.Red,
        label = "text color"
    )

    val icon = if(check) {
        Icons.Rounded.Check
    } else {
        Icons.Rounded.Close
    }
    Row {
        Icon(
            imageVector = icon,
            tint = color,
            contentDescription = "status icon"
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = condition,
            color = color
        )
    }
}