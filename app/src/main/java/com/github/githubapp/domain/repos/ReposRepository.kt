package com.github.githubapp.domain.repos

import com.github.githubapp.domain.models.RepoEventModel
import com.github.githubapp.domain.models.RepoModel
import kotlinx.coroutines.flow.Flow

interface ReposRepository {

    suspend fun getRepo(userName: String, repoName: String): Flow<RepoModel>

    suspend fun getRepoEvents(userName: String, repoName: String): List<RepoEventModel>
}
