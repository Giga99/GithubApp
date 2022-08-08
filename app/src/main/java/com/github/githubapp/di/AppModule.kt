package com.github.githubapp.di

import com.github.githubapp.common.ApiUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @ApiUrl
    fun provideApiUrl(): String = "https://api.github.com/"
}
