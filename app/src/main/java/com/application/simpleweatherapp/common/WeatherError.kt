package com.application.simpleweatherapp.common


sealed class WeatherError : Throwable() {
    object NetworkError : WeatherError()
    object CityNotFoundError : WeatherError()
    data class ServerError(val code: Int, val errorMessage: String?) : WeatherError()
    data class UnknownError(val errorMessage: String) : WeatherError()
}