package com.application.simpleweatherapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.application.simpleweatherapp.common.WeatherError
import com.application.simpleweatherapp.ui.home.Previews

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    error: WeatherError
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when (error) {
            is WeatherError.CityNotFoundError -> {
                Icon(
                    imageVector = Icons.Filled.LocationOff,
                    contentDescription = "404 error",
                    modifier = Modifier.size(144.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }

            is WeatherError.NetworkError -> {
                Icon(
                    imageVector = Icons.Filled.WifiOff,
                    contentDescription = "Network error",
                    modifier = Modifier.size(144.dp),
                    tint = MaterialTheme.colorScheme.error

                )
            }

            is WeatherError.ServerError -> {
                Icon(
                    imageVector = Icons.Filled.CloudOff,
                    contentDescription = "Server error",
                    modifier = Modifier.size(144.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }

            is WeatherError.UnknownError -> {
                Icon(
                    imageVector = Icons.Filled.Error,
                    contentDescription = "Server error",
                    modifier = Modifier.size(144.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Previews
@Composable
fun ErrorViewPreview() {
    ErrorView(
        error = WeatherError.UnknownError(
            "Something went wrong"
        )
    )
}
