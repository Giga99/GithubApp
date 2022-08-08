package com.github.githubapp.data.datasource

import com.github.githubapp.data.local.ReposDao
import com.github.githubapp.data.remote.services.UsersApiService
import com.github.githubapp.domain.mappers.toEntity
import com.github.githubapp.domain.mappers.toModel
import com.github.githubapp.domain.models.RepoModel
import com.github.githubapp.domain.repos.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApiService: UsersApiService,
    private val reposDao: ReposDao
) : UsersRepository {

    override suspend fun fetchUserRepositories(userName: String) = withContext(Dispatchers.IO) {
        try {
            val response = usersApiService.getUserRepos(userName).body() ?: emptyList()
            reposDao.insertRepos(response.map { it.toEntity() })
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun getUserRepositories(userName: String): Flow<List<RepoModel>> =
        withContext(Dispatchers.IO) {
            reposDao.getReposForOwner(userName).map { it.map { it.toModel() } }
        }
}
