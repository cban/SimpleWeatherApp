package com.application.simpleweatherapp.di

import com.application.simpleweatherapp.data.datasource.CurrentWeatherDataSourceImpl
import com.application.simpleweatherapp.data.repository.CurrentWeatherRepository
import com.application.simpleweatherapp.data.repository.CurrentWeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCurrentWeatherRepository(impl: CurrentWeatherRepositoryImpl): CurrentWeatherRepository
}