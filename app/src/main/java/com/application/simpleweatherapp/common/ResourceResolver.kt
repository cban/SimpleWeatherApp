package com.application.simpleweatherapp.common

import android.content.Context
import androidx.annotation.StringRes

class ResourceResolver(private val context: Context) {

    fun getString(@StringRes resId: Int): String {
        return context.resources.getString(resId)
    }
}