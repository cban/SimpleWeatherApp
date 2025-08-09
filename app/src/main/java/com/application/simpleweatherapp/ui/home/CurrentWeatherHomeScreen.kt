package com.application.simpleweatherapp.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.application.simpleweatherapp.R
import com.application.simpleweatherapp.ui.WeatherUiState
import com.application.simpleweatherapp.ui.components.LoadingIndicator
import com.application.simpleweatherapp.ui.components.PrimaryButton
import com.application.simpleweatherapp.ui.model.WeatherUiModel
import com.application.simpleweatherapp.ui.theme.Grey
import com.application.simpleweatherapp.ui.theme.SimpleWeatherAppTheme

@Composable
fun CurrentWeatherHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: CurrentWeatherViewModel = hiltViewModel(),
) {
    val weatherState by viewModel.weatherUiState.collectAsStateWithLifecycle()

    WeatherContent(
        onClick = { cityName -> viewModel.getWeather(cityName) },
        weatherState = weatherState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherContent(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {},
    weatherState: WeatherUiState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var cityName by remember { mutableStateOf("") }
            Text(
                stringResource(R.string.enter_city_name), modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyLarge
            )
            OutlinedTextField(
                value = cityName,
                onValueChange = { cityName = it },
                label = { Text(stringResource(R.string.city)) },
                modifier = Modifier.fillMaxWidth().padding( horizontal = 24.dp)
            )

            PrimaryButton(
                onClick = { onClick(cityName) },
                text = stringResource(R.string.get_weather),
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (weatherState) {
                is WeatherUiState.Loading -> {
                    LoadingIndicator()
                }

                is WeatherUiState.Success -> {
                    CurrentWeatherData(
                        modifier = modifier,
                        weather = weatherState.data
                    )
                }

                is WeatherUiState.Error -> {
                    Text(text = weatherState.message)
                }

                WeatherUiState.Idle -> {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            stringResource(R.string.no_weather_data_available),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CurrentWeatherData(
    modifier: Modifier = Modifier,
    weather: WeatherUiModel,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Grey
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    weather.cityName,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(16.dp)
                )
                WeatherIcon(weather.iconUrl)
                Text(
                    weather.temperatureCelsius,
                    style = MaterialTheme.typography.headlineLarge,
                )
                Text(weather.description, modifier = modifier.padding(bottom = 16.dp))
            }
        }
    }
}

@Composable
fun WeatherIcon(url: String) {
    AsyncImage(
        modifier = Modifier
            .size(128.dp)
            .padding(8.dp),
        model = url,
        placeholder = painterResource(id = R.drawable.placeholder_icon),
        contentDescription = "Icon",
    )

}

@Preview(showBackground = true)
@Composable
fun WeatherIconPreview() {
    SimpleWeatherAppTheme {
        WeatherIcon(url = "")
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherContentPreview() {
    val weatherUiModel = WeatherUiModel(
        cityName = "London",
        temperatureCelsius = "10°C",
        description = "Sunny",
        iconUrl = "https://openweathermap.org/img/wn/01d@2x.png",
        minTemperatureCelsius = "14.46°C",
        maxTemperatureCelsius = "30°C",
    )
    SimpleWeatherAppTheme {
        WeatherContent(
            onClick = {}, weatherState = WeatherUiState.Success(
                weatherUiModel

            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CurrentWeatherDataPreview() {
    val weatherUiModel = WeatherUiModel(
        cityName = "Cape Town",
        temperatureCelsius = "10°C",
        description = "Sunny",
        iconUrl = "",
        minTemperatureCelsius = "14.46°C",
        maxTemperatureCelsius = "30°C",
    )
    SimpleWeatherAppTheme {
        CurrentWeatherData(
            weather = weatherUiModel,
        )
    }
}