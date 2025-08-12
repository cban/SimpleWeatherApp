package com.application.simpleweatherapp.ui.home

import app.cash.turbine.test
import com.application.simpleweatherapp.common.CoroutineRule
import com.application.simpleweatherapp.common.ResourceResolver
import com.application.simpleweatherapp.common.Result
import com.application.simpleweatherapp.common.WeatherError
import com.application.simpleweatherapp.common.fakeWeatherData
import com.application.simpleweatherapp.data.repository.CurrentWeatherRepository
import com.application.simpleweatherapp.ui.WeatherUiState
import com.application.simpleweatherapp.ui.mapper.toUiMessage
import com.application.simpleweatherapp.ui.mapper.toUiModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrentWeatherViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    private val repository = mockk<CurrentWeatherRepository>()
    private val resourceResolver = mockk<ResourceResolver>()
    private lateinit var subjectUnderTest: CurrentWeatherViewModel

    @BeforeEach
    fun setup() {
        subjectUnderTest = CurrentWeatherViewModel(repository, resourceResolver)
    }

    @Test
    fun `getWeather emits Loading then Success given repository returns weather data`() =
        runTest {
            val fakeWeatherData = fakeWeatherData
            val expectedUiModel = fakeWeatherData.toUiModel()
            coEvery { repository.getCurrentWeather(any(), any()) } returns Result.Success(
                fakeWeatherData
            )

            subjectUnderTest.weatherUiState.test {
                subjectUnderTest.getWeather("Joburg")
                advanceUntilIdle()

                awaitItem()
                assertEquals(WeatherUiState.Loading, awaitItem())
                assertEquals(WeatherUiState.Success(expectedUiModel), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

        }

    @Test
    fun `getWeather emits Network issue Error given a failure response`() = runTest {
        val error = WeatherError.NetworkError
        val errorMessage = "Network issue"
        coEvery { repository.getCurrentWeather("Joburg", "metric") } returns Result.Error(error)
        coEvery { resourceResolver.getString(any()) } returns errorMessage
        coEvery { (error as WeatherError).toUiMessage(resourceResolver) } returns errorMessage


        subjectUnderTest.weatherUiState.test {
            subjectUnderTest.getWeather("Joburg")

            advanceUntilIdle()
            awaitItem()
            awaitItem()

            assertEquals(WeatherUiState.Error(errorMessage), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

}