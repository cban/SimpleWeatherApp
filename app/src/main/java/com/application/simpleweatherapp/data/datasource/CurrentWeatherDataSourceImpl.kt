package com.application.simpleweatherapp.data.datasource

import com.application.simpleweatherapp.common.Result
import com.application.simpleweatherapp.data.datasource.network.WeatherApi
import com.application.simpleweatherapp.data.model.CurrentWeatherResponse
import jakarta.inject.Inject

class CurrentWeatherDataSourceImpl @Inject constructor(private val apiService: WeatherApi) :
    CurrentWeatherDataSource {
    override suspend fun getCurrentWeather(
        cityName: String,
        units: String
    ): Result<CurrentWeatherResponse?> {
        return try {
            val response = apiService.getCurrentWeatherByCityName(cityName, units)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}