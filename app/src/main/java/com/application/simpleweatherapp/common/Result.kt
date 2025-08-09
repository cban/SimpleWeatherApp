package com.application.simpleweatherapp.common

sealed interface Result<out T> {
    class Success<T>(val data: T) : Result<T>
    data class Error(val throwable: Throwable) : Result<Nothing>
}
