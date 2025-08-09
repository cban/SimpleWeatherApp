package com.application.simpleweatherapp.data.repository

import com.application.simpleweatherapp.common.Result
import com.application.simpleweatherapp.data.model.CurrentWeatherResponse

interface CurrentWeatherRepository {
    suspend fun getCurrentWeather(cityName: String, units: String): Result<CurrentWeatherResponse?>
}