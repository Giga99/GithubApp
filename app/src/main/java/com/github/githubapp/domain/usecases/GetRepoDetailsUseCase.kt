package com.github.githubapp.domain.usecases

import com.github.githubapp.common.Result
import com.github.githubapp.domain.models.RepoDetailsModel
import com.github.githubapp.domain.repos.ReposRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRepoDetailsUseCase @Inject constructor(
    private val reposRepository: ReposRepository
) {

    suspend operator fun invoke(
        ownerName: String,
        repoName: String
    ): Flow<Result<RepoDetailsModel>> =
        withContext(Dispatchers.IO) {
            val repoFlow = reposRepository.getRepo(ownerName, repoName)
            val repoEvent = reposRepository.getRepoEvents(ownerName, repoName)
            if (repoEvent is Result.Error)
                flowOf<Result<RepoDetailsModel>>(Result.Error(repoEvent.message ?: ""))

            repoFlow.map { Result.Success(RepoDetailsModel(it, repoEvent.data?.firstOrNull())) }
        }
}
