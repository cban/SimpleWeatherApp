package com.application.simpleweatherapp.data.datasource.network

import com.application.simpleweatherapp.data.model.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getCurrentWeatherByCityName(
        @Query("q") cityName: String,
        @Query("units") units: String,
    ): CurrentWeatherResponse
}