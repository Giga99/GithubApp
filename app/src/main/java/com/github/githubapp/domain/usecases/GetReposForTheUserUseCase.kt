package com.github.githubapp.domain.usecases

import com.github.githubapp.common.Result
import com.github.githubapp.domain.models.RepoModel
import com.github.githubapp.domain.repos.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetReposForTheUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {

    suspend operator fun invoke(username: String): Flow<Result<List<RepoModel>>> =
        withContext(Dispatchers.IO) {
            val result = usersRepository.fetchUserRepositories(username)
            if (result is Result.Error)
               return@withContext flowOf<Result<List<RepoModel>>>(Result.Error(result.message ?: ""))

            val reposFlow = usersRepository.getUserRepositories(username)

            reposFlow.map { Result.Success(it) }
        }
}
