package com.example.puleya.ui.view.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.puleya.R

@Composable
fun SplashScreen(navigateToHome: () -> Unit) {

    // Define scale animation
    val scale by remember { mutableFloatStateOf(1f) }

    //Define an animation
    val animatedScale = animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    // Launch effect to delay and navigate
    LaunchedEffect(true) {

        // Keep splash for 5 seconds
        delay(5000)

        // Navigate to the next screen
        navigateToHome()
    }

    // UI Layout
    Surface(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(0.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.launcher), // Use your app icon
                contentDescription = "App Icon",
                modifier = Modifier.scale(animatedScale.value)
            )
        }
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SplashScreen {}
}
