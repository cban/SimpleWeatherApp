package com.application.simpleweatherapp.common

import com.application.simpleweatherapp.data.model.Clouds
import com.application.simpleweatherapp.data.model.Coord
import com.application.simpleweatherapp.data.model.CurrentWeatherResponse
import com.application.simpleweatherapp.data.model.Main
import com.application.simpleweatherapp.data.model.Sys
import com.application.simpleweatherapp.data.model.Weather
import com.application.simpleweatherapp.data.model.Wind

val fakeWeatherData = CurrentWeatherResponse(
    coord = Coord(lon = -26.2022, lat = 28.04363),
    weather = listOf(
        Weather(id = 800, main = "Clear", description = "clear sky", icon = "01d")
    ),
    base = "station",
    main = Main(
        temp = 20.5,
        feelsLike = 19.8,
        tempMin = 18.0,
        tempMax = 22.0,
        pressure = 1012,
        humidity = 60,
        seaLevel = 1012,
        groundLevel = 933
    ),
    visibility = 10000,
    wind = Wind(speed = 3.6, deg = 240, gust = 4.7),
    clouds = Clouds(all = 0),
    dt = 1628164800L,
    sys = Sys(
        country = "GB",
        sunrise = 112,
        sunset = 112
    ),
    timezone = 3600,
    id = 122,
    name = "Johannesbutg",
    cod = 200
)