package com.github.githubapp.domain.usecases

import com.github.githubapp.common.Result
import com.github.githubapp.domain.models.RepoModel
import com.github.githubapp.domain.repos.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllReposUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {

    suspend operator fun invoke(): Flow<Result<List<RepoModel>>> =
        withContext(Dispatchers.IO) {
            val jake = "JakeWharton"
            val infinum = "infinum"

            val jakeResult = usersRepository.fetchUserRepositories(jake)
            if (jakeResult is Result.Error)
                flowOf<Result<List<RepoModel>>>(Result.Error(jakeResult.message ?: ""))

            val infinumResult = usersRepository.fetchUserRepositories(infinum)
            if (infinumResult is Result.Error)
                flowOf<Result<List<RepoModel>>>(Result.Error(infinumResult.message ?: ""))

            val jakeReposFlow = usersRepository.getUserRepositories(jake)
            val infinumReposFlow = usersRepository.getUserRepositories(infinum)

            jakeReposFlow.combine(infinumReposFlow) { jakeRepos, infinumRepos ->
                Result.Success(
                    jakeRepos.plus(infinumRepos)
                        .sortedByDescending { it.stargazersCount + it.watchersCount + it.forksCount }
                )
            }
        }
}
