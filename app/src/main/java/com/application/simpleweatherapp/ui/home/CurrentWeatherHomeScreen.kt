package com.application.simpleweatherapp.ui.home


import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.application.simpleweatherapp.R
import com.application.simpleweatherapp.ui.WeatherUiState
import com.application.simpleweatherapp.ui.components.LoadingIndicator
import com.application.simpleweatherapp.ui.components.PrimaryButton
import com.application.simpleweatherapp.ui.components.WeatherIcon
import com.application.simpleweatherapp.ui.model.WeatherUiModel
import com.application.simpleweatherapp.ui.theme.Grey
import com.application.simpleweatherapp.ui.theme.SimpleWeatherAppTheme

@Composable
fun CurrentWeatherHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: CurrentWeatherViewModel = hiltViewModel(),
) {
    val weatherState by viewModel.weatherUiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.snackBarMessage) {
        viewModel.snackBarMessage.collect { message ->
            snackBarHostState.showSnackbar(message)
        }
    }

    WeatherContent(
        onClick = { cityName -> viewModel.getWeather(cityName) },
        weatherState = weatherState,
        snackBarHostState = snackBarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherContent(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {},
    snackBarHostState: SnackbarHostState,
    weatherState: WeatherUiState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(8.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CityInputCard(
                onSubmit = onClick,
                uiState = weatherState,
                modifier = modifier
            )

            Spacer(modifier = Modifier.height(16.dp))

            WeatherInfoContent(
                weatherState = weatherState,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
internal fun WeatherInfoContent(
    weatherState: WeatherUiState,
    modifier: Modifier = Modifier,
) {
    when (weatherState) {
        is WeatherUiState.Loading -> LoadingIndicator()
        is WeatherUiState.Success -> CurrentWeatherData(
            modifier = modifier,
            weather = weatherState.data
        )

        is WeatherUiState.Error -> {
        }

        WeatherUiState.Idle -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(R.string.no_weather_data_available),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
private fun CityInputCard(
    onSubmit: (String) -> Unit,
    uiState: WeatherUiState,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
    ) {
        var cityName by remember { mutableStateOf("") }
        var showError by remember { mutableStateOf(false) }
        Text(
            stringResource(R.string.enter_city_name), modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyLarge
        )
        OutlinedTextField(
            value = cityName,
            onValueChange = {
                cityName = it
                if (showError && it.isNotEmpty()) {
                    showError = false
                }
            },
            label = { Text(stringResource(R.string.city)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            isError = showError
        )
        if (showError) {
            Text(
                text = stringResource(R.string.error_field_cannot_be_empty),
                color = Color.Red,
                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                )
            )
        }

        PrimaryButton(
            onClick = {
                if (cityName.isBlank()) {
                    showError = true
                } else {
                    onSubmit(cityName)
                }
            },
            enabled = uiState !is WeatherUiState.Loading,
            text = stringResource(R.string.get_weather),
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
private fun CurrentWeatherData(
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
                Text(
                    weather.description,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    name = "day mode",
    uiMode = UI_MODE_NIGHT_NO
)

@Preview(
    showBackground = true,
    name = "night mode",
    uiMode = UI_MODE_NIGHT_YES
)
annotation class Previews


@Previews
@Composable
fun WeatherIconPreview() {
    SimpleWeatherAppTheme {
        WeatherIcon(url = "")
    }
}

@Previews
@Composable
fun CityInputCardPreview() {
    SimpleWeatherAppTheme {
        CityInputCard(onSubmit = {}, uiState = WeatherUiState.Idle)
    }
}


@Previews
@Composable
fun CurrentWeatherDataPreview() {
    val weatherUiModel = WeatherUiModel(
        cityName = "Cape Town",
        temperatureCelsius = "10°C",
        description = "Sunny",
        iconUrl = "",
    )
    SimpleWeatherAppTheme {
        CurrentWeatherData(
            weather = weatherUiModel,
        )
    }
}

@Previews
@Composable
fun WeatherContentPreview() {
    val weatherUiModel = WeatherUiModel(
        cityName = "London",
        temperatureCelsius = "10°C",
        description = "Sunny",
        iconUrl = "https://openweathermap.org/img/wn/01d@2x.png",
    )
    SimpleWeatherAppTheme {
        WeatherContent(
            onClick = {}, weatherState = WeatherUiState.Success(
                weatherUiModel

            ),
            snackBarHostState = SnackbarHostState()
        )
    }
}