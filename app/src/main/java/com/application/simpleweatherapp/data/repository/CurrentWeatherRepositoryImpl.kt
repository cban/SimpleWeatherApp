package com.application.simpleweatherapp.data.repository

import com.application.simpleweatherapp.common.Result
import com.application.simpleweatherapp.common.WeatherError
import com.application.simpleweatherapp.data.datasource.CurrentWeatherDataSource
import com.application.simpleweatherapp.data.model.CurrentWeatherResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CurrentWeatherRepositoryImpl @Inject constructor(
    private val dataSource: CurrentWeatherDataSource
) : CurrentWeatherRepository {
    override suspend fun getCurrentWeather(
        cityName: String,
        units: String
    ): Result<CurrentWeatherResponse?> {
        val result = dataSource.getCurrentWeather(cityName, units)
        return when (result) {
            is Result.Success -> {
                val data = result.data
                if (data != null) {
                    Result.Success(data)
                } else {
                    Result.Error(Exception("No weather data received"))
                }
            }

            is Result.Error -> Result.Error(mapExceptionToWeatherError(result.throwable))
        }
    }

    private fun mapExceptionToWeatherError(e: Throwable): WeatherError {
        return when (e) {
            is IOException -> WeatherError.NetworkError
            is HttpException -> {
                if (e.code() == 404) WeatherError.CityNotFoundError
                else WeatherError.ServerError(e.code(), e.message())
            }

            else -> WeatherError.UnknownError(e.localizedMessage ?: "Unknown error")
        }
    }
}