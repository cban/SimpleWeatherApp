package com.application.simpleweatherapp.data.repository

import com.application.simpleweatherapp.common.Result
import com.application.simpleweatherapp.common.WeatherError
import com.application.simpleweatherapp.data.datasource.CurrentWeatherDataSource
import com.application.simpleweatherapp.data.model.CurrentWeatherResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class CurrentWeatherRepositoryImplTest {

    private var mockDataSource: CurrentWeatherDataSource = mockk()
    private lateinit var subjectUnderTest: CurrentWeatherRepositoryImpl


    @BeforeEach
    fun setUp() {
        subjectUnderTest = CurrentWeatherRepositoryImpl(mockDataSource)
    }

    @Test
    fun `getCurrentWeather returns Success when data source returns Success with  data`() =
        runTest {
            val mockResponse = mockk<CurrentWeatherResponse>()
            coEvery { mockDataSource.getCurrentWeather("city", "units") } returns Result.Success(
                mockResponse
            )

            val result = subjectUnderTest.getCurrentWeather("city", "units")

            assertTrue(result is Result.Success)
            assertEquals(mockResponse, (result as Result.Success).data)
        }

    @Test
    fun `mapExceptionToWeatherError maps IOException to WeatherError_NetworkError`() = runTest {
        val exception = IOException("Network failure")
        coEvery { mockDataSource.getCurrentWeather(any(), any()) } returns Result.Error(exception)

        val result = subjectUnderTest.getCurrentWeather("city", "units")

        assertTrue(result is Result.Error)
        assertEquals(WeatherError.NetworkError, (result as Result.Error).throwable)
    }

    @Test
    fun `mapExceptionToWeatherError maps HttpException  to WeatherError_CityNotFound`() =
        runTest {
            val exception = HttpException(Response.error<Any>(404, "".toResponseBody(null)))
            coEvery { mockDataSource.getCurrentWeather(any(), any()) } returns Result.Error(
                exception
            )

            val result = subjectUnderTest.getCurrentWeather("city", "units")

            assertTrue(result is Result.Error)
            assertEquals(WeatherError.CityNotFoundError, (result as Result.Error).throwable)

        }

    @Test
    fun `mapExceptionToWeatherError maps HttpException to WeatherError_ServerError`() =
        runTest {
            val exception = HttpException(
                Response.error<Any>(
                    500,
                    "Internal server error".toResponseBody()
                )
            )
            coEvery { mockDataSource.getCurrentWeather(any(), any()) } returns Result.Error(
                exception
            )

            val result = subjectUnderTest.getCurrentWeather("city", "units")

            assertTrue(result is Result.Error)
            assertEquals(
                WeatherError.ServerError(500, "Internal server error"),
                (result as Result.Error).throwable
            )

        }

}