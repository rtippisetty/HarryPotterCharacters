package dev.ranga.hpcharacters.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.ranga.hpcharacters.Analytics.Logger
import dev.ranga.hpcharacters.Analytics.LoggerImpl

@Module
@InstallIn(ViewModelComponent::class)
interface AnalyticsModule {

    @Binds
    fun bindLogger(loggerImpl: LoggerImpl): Logger
}