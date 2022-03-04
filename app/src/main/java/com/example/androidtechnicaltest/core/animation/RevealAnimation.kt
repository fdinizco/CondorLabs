package com.example.androidtechnicaltest.core.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RevealAnimation(image: Int, onTimeout: () -> Unit) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)
    val animateShape = remember { Animatable(200f) }
    LaunchedEffect(animateShape) {
        animateShape.animateTo(
            targetValue = 4f,
            animationSpec = repeatable(
                animation = tween(
                    durationMillis = 2000,
                    easing = LinearEasing,
                    delayMillis = 100
                ),
                repeatMode = RepeatMode.Restart,
                iterations = 1
            )
        )
        currentOnTimeout()
    }

    Box(
        modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .border(
                width = Dp(animateShape.value),
                color = Color.Black,
                shape = CircleShape
            )
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Compose image",
            modifier = Modifier.size(200.dp).align(alignment = Alignment.Center),
            colorFilter = ColorFilter.tint(
                Color.Yellow,
                BlendMode.ColorBurn
            ),
        )
    }
}