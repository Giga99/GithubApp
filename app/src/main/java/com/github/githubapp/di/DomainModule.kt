package com.github.githubapp.di

import com.github.githubapp.data.datasource.ReposRepositoryImpl
import com.github.githubapp.data.datasource.UsersRepositoryImpl
import com.github.githubapp.data.local.GithubDatabase
import com.github.githubapp.data.remote.services.RepoApiService
import com.github.githubapp.data.remote.services.UsersApiService
import com.github.githubapp.domain.repos.ReposRepository
import com.github.githubapp.domain.repos.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun providesUsersRepository(
        usersApiService: UsersApiService,
        githubDatabase: GithubDatabase
    ): UsersRepository = UsersRepositoryImpl(
        usersApiService = usersApiService,
        reposDao = githubDatabase.reposDao
    )

    @Singleton
    @Provides
    fun providesReposRepository(
        repoApiService: RepoApiService,
        githubDatabase: GithubDatabase
    ): ReposRepository = ReposRepositoryImpl(
        repoApiService = repoApiService,
        reposDao = githubDatabase.reposDao
    )
}
