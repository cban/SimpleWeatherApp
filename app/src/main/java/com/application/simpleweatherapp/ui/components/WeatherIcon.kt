package com.application.simpleweatherapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.application.simpleweatherapp.R

@Composable
fun WeatherIcon(url: String, modifier: Modifier = Modifier) {
    SubcomposeAsyncImage(
        modifier = modifier
            .size(144.dp)
            .padding(8.dp),
        model = url,
        contentDescription = "Icon",
        loading = {
            Image(
                painter = painterResource(id = R.drawable.placeholder_icon),
                contentDescription = null
            )
        },
        error = {
            Image(
                painter = painterResource(id = R.drawable.placeholder_icon),
                contentDescription = null
            )
        }
    )
}