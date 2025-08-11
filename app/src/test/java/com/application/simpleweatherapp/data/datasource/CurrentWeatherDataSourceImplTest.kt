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

    @BeforeEach
    fun setUp() {
        subjectUnderTest = CurrentWeatherDataSourceImpl(apiService)
    }

    @Test
    fun `verify getCurrentWeather should returns weather data`() = runTest {
        val mockedWeatherData = mockk<CurrentWeatherResponse>()

        coEvery {
            apiService.getCurrentWeatherByCityName(
                "city",
                "units"
            )
        } returns mockedWeatherData
        val result = subjectUnderTest.getCurrentWeather("city", "units")

        Assertions.assertTrue(result is Result.Success<CurrentWeatherResponse?>)
        Assertions.assertEquals(mockedWeatherData, (result as Result.Success).data)
        coVerify(exactly = 1) { apiService.getCurrentWeatherByCityName("hello", "units") }
    }

    @Test
    fun `verify getCurrentWeather returns Error when api call throws exception`() = runTest {
        val exception = Exception("Network error")

        coEvery { apiService.getCurrentWeatherByCityName("city", "units") } throws exception
        val result = subjectUnderTest.getCurrentWeather("city", "units")

        Assertions.assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).throwable)
        coVerify(exactly = 1) { apiService.getCurrentWeatherByCityName("city", "units") }

    }

    @Test
    fun `getCurrentWeather returns Error when API call throws IOException`() = runTest {
        val ioException = IOException("Network issue")
        coEvery { apiService.getCurrentWeatherByCityName(any(), any()) } throws ioException

        val result = subjectUnderTest.getCurrentWeather("city", "units")
        Assertions.assertTrue(result is Result.Error)

        assertEquals(ioException, (result as Result.Error).throwable)
    }

}