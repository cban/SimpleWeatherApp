package com.application.simpleweatherapp.ui.mapper

import com.application.simpleweatherapp.R
import com.application.simpleweatherapp.common.ResourceResolver
import com.application.simpleweatherapp.common.WeatherError
import com.application.simpleweatherapp.data.model.CurrentWeatherResponse
import com.application.simpleweatherapp.ui.model.WeatherUiModel


fun CurrentWeatherResponse?.toUiModel(): WeatherUiModel {
    val iconCode = this?.weather?.firstOrNull()?.icon ?: ""
    return WeatherUiModel(
        cityName = this?.name ?: "Unknown",
        temperatureCelsius = this?.main?.temp?.let { "%.1f°C".format(it) } ?: "--",
        description = this?.weather?.firstOrNull()?.description ?: "N/A",
        iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png"
    )
}

fun WeatherError.toUiMessage(resourceResolver: ResourceResolver): String = when (this) {
    is WeatherError.NetworkError -> resourceResolver.getString(R.string.error_no_internet)
    is WeatherError.CityNotFoundError -> resourceResolver.getString(R.string.error_city_not_found)
    is WeatherError.ServerError -> resourceResolver.getString(R.string.error_server)
    is WeatherError.UnknownError -> resourceResolver.getString(R.string.error_unknown)
}

