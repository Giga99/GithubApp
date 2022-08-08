package com.github.githubapp.di

import com.github.githubapp.data.remote.adapters.RepoEventEnumAdapter
import com.squareup.moshi.JsonAdapter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkAdaptersModule {

    @Binds
    @IntoSet
    abstract fun bindRepoEventTypeAdapter(adapter: RepoEventEnumAdapter): JsonAdapter<*>
}
