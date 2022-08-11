package com.github.githubapp.domain.repos

import com.github.githubapp.common.Result
import com.github.githubapp.domain.models.RepoModel
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun fetchUserRepositories(userName: String): Result<Unit>

    suspend fun getUserRepositories(userName: String): Flow<List<RepoModel>>
}
