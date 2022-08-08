package com.github.githubapp.data.datasource

import com.github.githubapp.data.local.ReposDao
import com.github.githubapp.data.remote.services.RepoApiService
import com.github.githubapp.domain.mappers.toModel
import com.github.githubapp.domain.models.RepoEventModel
import com.github.githubapp.domain.models.RepoModel
import com.github.githubapp.domain.repos.ReposRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReposRepositoryImpl @Inject constructor(
    private val repoApiService: RepoApiService,
    private val reposDao: ReposDao
) : ReposRepository {

    override suspend fun getRepo(userName: String, repoName: String): Flow<RepoModel> =
        withContext(Dispatchers.IO) {
            reposDao.getRepo(userName, repoName).map { it.toModel() }
        }

    override suspend fun getRepoEvents(userName: String, repoName: String): List<RepoEventModel> =
        withContext(Dispatchers.IO) {
            repoApiService.getRepoEvents(userName, repoName).map { it.toModel() }
        }
}
