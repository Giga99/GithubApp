package com.github.githubapp.domain.usecases

import com.github.githubapp.common.Result
import com.github.githubapp.domain.models.RepoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllReposUseCase @Inject constructor(
    private val getReposForTheUserUseCase: GetReposForTheUserUseCase,
) {

    suspend operator fun invoke(): Flow<List<Result<List<RepoModel>>>> =
        withContext(Dispatchers.IO) {
            val jake = "JakeWharton"
            val infinum = "infinum"

            val jakeResult = getReposForTheUserUseCase(jake)
            val infinumResult = getReposForTheUserUseCase(infinum)

            jakeResult.combine(infinumResult) { jakeRepos, infinumRepos ->
                val allRepos = mutableListOf<RepoModel>()

                if (jakeRepos is Result.Success)
                    allRepos.addAll(jakeRepos.data ?: emptyList())
                if (infinumRepos is Result.Success)
                    allRepos.addAll(infinumRepos.data ?: emptyList())

                val allReposResult =
                    if (allRepos.isEmpty()) Result.Error(jakeRepos.message ?: "")
                    else Result.Success(allRepos.sortedByDescending { it.forksCount + it.watchersCount + it.stargazersCount })

                listOf(allReposResult, jakeRepos, infinumRepos)
            }
        }
}
