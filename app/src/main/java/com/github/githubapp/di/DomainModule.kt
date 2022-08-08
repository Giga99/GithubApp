package com.github.githubapp.di

import com.github.githubapp.data.datasource.ReposRepositoryImpl
import com.github.githubapp.data.datasource.UsersRepositoryImpl
import com.github.githubapp.domain.repos.ReposRepository
import com.github.githubapp.domain.repos.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindUsersRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository

    @Binds
    abstract fun bindReposRepository(reposRepositoryImpl: ReposRepositoryImpl): ReposRepository
}
