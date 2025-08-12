package com.application.simpleweatherapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.application.simpleweatherapp.ui.WeatherUiState
import com.application.simpleweatherapp.ui.home.WeatherInfoContent
import com.application.simpleweatherapp.ui.model.WeatherUiModel
import org.junit.Rule
import org.junit.Test

class WeatherInfoContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun weatherInfoContent_displaysIdleMessage_whenIdleState() {
        composeTestRule.setContent {
            WeatherInfoContent(weatherState = WeatherUiState.Idle)
        }

        composeTestRule.onNodeWithText("No Weather data available").assertIsDisplayed()
    }


    @Test
    fun weatherInfoContent_displaysLoading_whenLoadingState() {
        composeTestRule.setContent {
            WeatherInfoContent(weatherState = WeatherUiState.Loading)
        }
        composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
    }

    @Test
    fun weatherInfoContent_displaysWeatherData_whenSuccessState() {
        val fakeWeather = WeatherUiModel(
            description = "Sunny", iconUrl = "", cityName = "Joburg",
            temperatureCelsius = "25.0"
        )

        composeTestRule.setContent {
            WeatherInfoContent(weatherState = WeatherUiState.Success(fakeWeather))
        }
        composeTestRule.onNodeWithText("Sunny").assertIsDisplayed()
        composeTestRule.onNodeWithText("25.0").assertIsDisplayed()
        composeTestRule.onNodeWithText("Joburg").assertIsDisplayed()
    }


}