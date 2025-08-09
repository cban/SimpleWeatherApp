package com.application.simpleweatherapp.data.datasource

import com.application.simpleweatherapp.common.Result
import com.application.simpleweatherapp.data.model.CurrentWeatherResponse


interface CurrentWeatherDataSource {

    suspend fun getCurrentWeather(
        cityName: String, units: String
    ): Result<CurrentWeatherResponse?>
}