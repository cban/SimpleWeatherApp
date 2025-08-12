package com.application.simpleweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.application.simpleweatherapp.ui.home.CurrentWeatherHomeScreen
import com.application.simpleweatherapp.ui.home.Previews
import com.application.simpleweatherapp.ui.theme.SimpleWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleWeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CurrentWeatherHomeScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(top = 24.dp)
                    )
                }
            }
        }
    }
}


@Previews
@Composable
fun CurrentWeatherHomeScreenPreview() {
    SimpleWeatherAppTheme {
        CurrentWeatherHomeScreen()
    }
}