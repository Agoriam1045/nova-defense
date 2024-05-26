package nova.defense.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import nova.defense.R
import nova.defense.navigation.Screens


@Composable
fun StartScreen(
    navController: NavHostController,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.nova_wallpaper),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }

    var toggledWelcome by remember { mutableStateOf(false) }

    val animatedWelcomePadding by animateFloatAsState(
        if (!toggledWelcome) {
            0f
        } else {
            1f
        },

        label = "padding"
    )

    LaunchedEffect(Unit) {
        toggledWelcome = true;
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        AnimatedVisibility(
            visible = toggledWelcome,
            enter = fadeIn(initialAlpha = animatedWelcomePadding, animationSpec = tween(durationMillis = 2000, easing = LinearEasing)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000)),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Welcome to")
            }
        }
        AnimatedVisibility(
            visible = toggledWelcome,
            enter = fadeIn(initialAlpha = animatedWelcomePadding, animationSpec = tween(durationMillis = 4000, easing = LinearEasing, delayMillis = 1500)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000)),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                Text(
                    text = "NovaDefense",
                    modifier = Modifier,
                    fontSize = 40.sp
                )
                Spacer(Modifier.height(100.dp))
            }
        }
        AnimatedVisibility(
            visible = toggledWelcome,
            enter = fadeIn(initialAlpha = animatedWelcomePadding, animationSpec = tween(durationMillis = 2000, easing = LinearEasing, delayMillis = 3000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000)),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Button(
                    modifier = Modifier.width(100.dp),
                    onClick = { navController.navigate(Screens.LogInScreen.route) }
                ) {
                    Text(text = "Log In")
                }
                Button(
                    modifier = Modifier.width(100.dp),
                    onClick = { navController.navigate(Screens.SignUpScreen.route) }
                ) {
                    Text(text = "Sign Up")
                }
            }
        }
    }
}
