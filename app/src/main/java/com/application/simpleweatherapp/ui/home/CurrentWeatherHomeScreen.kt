package com.application.simpleweatherapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.application.simpleweatherapp.R
import com.application.simpleweatherapp.ui.WeatherUiState
import com.application.simpleweatherapp.ui.components.LoadingIndicator
import com.application.simpleweatherapp.ui.model.WeatherUiModel

@Composable
fun CurrentWeatherHomeScreen(
    modifier: Modifier = Modifier,

    viewModel: CurrentWeatherViewModel = hiltViewModel()
) {
    val weatherState by viewModel.weatherUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getWeather("Johannesburg")
    }
    when (weatherState) {
        is WeatherUiState.Loading -> {
            LoadingIndicator()
        }

        is WeatherUiState.Success -> {
            CurrentWeatherContent(
                modifier = modifier,
                weather = (weatherState as WeatherUiState.Success).data
            )
        }

        is WeatherUiState.Error -> {
            Text(text = (weatherState as WeatherUiState.Error).message)
        }

        WeatherUiState.Idle -> {
            Text(stringResource(R.string.no_weather_data_available))
        }

    }
}

@Composable
fun CurrentWeatherContent(
    modifier: Modifier = Modifier,
    weather: WeatherUiModel
) {
    Column {
        Text(weather.cityName)
        Text(weather.temperatureCelsius)
        Text(weather.description)
        AsyncImage(
            modifier = Modifier
                .size(148.dp)
                .padding(8.dp),
            model = weather.iconUrl,
            contentDescription = "Icon",
        )


    }

}