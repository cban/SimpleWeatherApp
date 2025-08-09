package com.application.simpleweatherapp.di

import android.content.Context
import com.application.simpleweatherapp.common.ResourceResolver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UiModule {
    @Provides
    @Singleton
    fun provideResourceResolver(@ApplicationContext context: Context): ResourceResolver {
        return ResourceResolver(context)
    }
}