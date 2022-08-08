package com.github.githubapp.data.datasource

import com.github.githubapp.common.Result
import com.github.githubapp.common.Result.Error
import com.github.githubapp.common.Result.Success
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
import timber.log.Timber
import javax.inject.Inject

class ReposRepositoryImpl @Inject constructor(
    private val repoApiService: RepoApiService,
    private val reposDao: ReposDao
) : ReposRepository {

    override suspend fun getRepo(ownerName: String, repoName: String): Flow<RepoModel> =
        withContext(Dispatchers.IO) {
            reposDao.getRepo(ownerName, repoName).map { it.toModel() }
        }

    override suspend fun getRepoEvents(
        ownerName: String,
        repoName: String
    ): Result<List<RepoEventModel>> =
        withContext(Dispatchers.IO) {
            try {
                val response =
                    repoApiService.getRepoEvents(ownerName, repoName).body() ?: emptyList()
                Success(response.map { it.toModel() })
            } catch (e: Exception) {
                Timber.e(e)
                Error(e.message ?: "")
            }
        }
}
