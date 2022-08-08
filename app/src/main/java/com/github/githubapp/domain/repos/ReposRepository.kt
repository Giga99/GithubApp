package com.github.githubapp.domain.repos

import com.github.githubapp.common.Result
import com.github.githubapp.domain.models.RepoEventModel
import com.github.githubapp.domain.models.RepoModel
import kotlinx.coroutines.flow.Flow

interface ReposRepository {

    suspend fun getRepo(ownerName: String, repoName: String): Flow<RepoModel>

    suspend fun getRepoEvents(ownerName: String, repoName: String): Result<List<RepoEventModel>>
}
