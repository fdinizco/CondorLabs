package com.example.androidtechnicaltest.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.androidtechnicaltest.R
import com.example.androidtechnicaltest.core.animation.RevealAnimation
import com.example.androidtechnicaltest.core.navigation.navigateToActivity
import com.example.androidtechnicaltest.core.theme.AndroidTechnicalTestTheme
import com.example.androidtechnicaltest.ui.main.MainActivity

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTechnicalTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    RevealAnimation(
                        image = R.drawable.ic_splash_image,
                        onTimeout = {
                            navigateToActivity(
                                context = this,
                                targetActivity = MainActivity::class.java,
                                intentFlag = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            )
                        }
                    )
                }
            }
        }
    }
}