package com.application.simpleweatherapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.simpleweatherapp.common.ResourceResolver
import com.application.simpleweatherapp.common.Result
import com.application.simpleweatherapp.common.WeatherError
import com.application.simpleweatherapp.data.repository.CurrentWeatherRepository
import com.application.simpleweatherapp.ui.WeatherUiState
import com.application.simpleweatherapp.ui.mapper.toUiMessage
import com.application.simpleweatherapp.ui.mapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val repository: CurrentWeatherRepository,
    private val resourceResolver: ResourceResolver
) : ViewModel() {

    private val _weatherUiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val weatherUiState: StateFlow<WeatherUiState> = _weatherUiState.asStateFlow()
    fun getWeather(city: String, units: String = "metric") {
        viewModelScope.launch {
            _weatherUiState.value = WeatherUiState.Loading
            val response = repository.getCurrentWeather(city, units)
            when (response) {
                is Result.Success -> _weatherUiState.value =
                    WeatherUiState.Success(response.data.toUiModel())

                is Result.Error -> {
                    val message = (response.throwable as WeatherError).toUiMessage(resourceResolver)
                    _weatherUiState.value = WeatherUiState.Error(message)
                }
            }
        }
    }
}