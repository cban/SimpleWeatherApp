package com.application.simpleweatherapp.data.datasource

import com.application.simpleweatherapp.common.Result
import com.application.simpleweatherapp.data.datasource.network.WeatherApi
import com.application.simpleweatherapp.data.model.CurrentWeatherResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

class CurrentWeatherDataSourceImplTest {

    private var apiService: WeatherApi = mockk<WeatherApi>()
    private lateinit var subjectUnderTest: CurrentWeatherDataSourceImpl
    private lateinit var cityName: String
    private lateinit var units: String

    @BeforeEach
    fun setUp() {
        subjectUnderTest = CurrentWeatherDataSourceImpl(apiService)
        cityName = "joburg"
        units = "metric"
    }

    @Test
    fun `verify getCurrentWeather should returns weather data`() = runTest {
        val mockedWeatherData = mockk<CurrentWeatherResponse>()

        coEvery {
            apiService.getCurrentWeatherByCityName(
                cityName,
                units
            )
        } returns mockedWeatherData
        val result = subjectUnderTest.getCurrentWeather(cityName, units)

        Assertions.assertTrue(result is Result.Success<CurrentWeatherResponse?>)
        assertEquals(mockedWeatherData, (result as Result.Success).data)
        coVerify(exactly = 1) { apiService.getCurrentWeatherByCityName(cityName, units) }
    }

    @Test
    fun `verify getCurrentWeather returns Error when api call throws exception`() = runTest {
        val exception = Exception("Network error")

        coEvery { apiService.getCurrentWeatherByCityName(cityName, units) } throws exception
        val result = subjectUnderTest.getCurrentWeather(cityName, units)

        Assertions.assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).throwable)
        coVerify(exactly = 1) { apiService.getCurrentWeatherByCityName(cityName, units) }

    }

    @Test
    fun `getCurrentWeather returns Error when API call throws IOException`() = runTest {
        val ioException = IOException("Network issue")
        coEvery { apiService.getCurrentWeatherByCityName(any(), any()) } throws ioException

        val result = subjectUnderTest.getCurrentWeather(cityName, units)
        Assertions.assertTrue(result is Result.Error)

        assertEquals(ioException, (result as Result.Error).throwable)
    }

}