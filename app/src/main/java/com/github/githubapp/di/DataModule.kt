package com.github.githubapp.di

import android.app.Application
import androidx.room.Room
import com.github.githubapp.common.ApiUrl
import com.github.githubapp.data.local.GithubDatabase
import com.github.githubapp.data.remote.services.RepoApiService
import com.github.githubapp.data.remote.services.UsersApiService
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideMoshi(
        adapters: Set<@JvmSuppressWildcards JsonAdapter<*>>
    ): Moshi = Moshi.Builder()
        .apply { adapters.forEach { adapter -> add(adapter) } }
        .build()

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(
        moshi: Moshi
    ): MoshiConverterFactory = MoshiConverterFactory.create(moshi)

    @Singleton
    @Provides
    fun provideRetrofit(
        @ApiUrl apiUrl: String,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .addConverterFactory(moshiConverterFactory)
        .build()

    @Singleton
    @Provides
    fun provideUsersApiService(
        retrofit: Retrofit
    ): UsersApiService = retrofit.create(UsersApiService::class.java)

    @Singleton
    @Provides
    fun provideRepoApiService(
        retrofit: Retrofit
    ): RepoApiService = retrofit.create(RepoApiService::class.java)

    @Singleton
    @Provides
    fun provideGithubDatabase(app: Application): GithubDatabase =
        Room.databaseBuilder(
            app,
            GithubDatabase::class.java,
            "github_db"
        ).build()
}
