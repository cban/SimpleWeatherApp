package com.application.simpleweatherapp.ui

import com.application.simpleweatherapp.ui.model.WeatherUiModel

sealed interface WeatherUiState {
    object Loading : WeatherUiState
    object Idle : WeatherUiState
    data class Success(val data: WeatherUiModel) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}
