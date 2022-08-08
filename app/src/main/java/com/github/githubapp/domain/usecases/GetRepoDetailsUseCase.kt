package com.github.githubapp.domain.usecases

import com.github.githubapp.domain.models.RepoDetailsModel
import com.github.githubapp.domain.repos.ReposRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRepoDetailsUseCase @Inject constructor(
    private val reposRepository: ReposRepository
) {

    suspend operator fun invoke(ownerName: String, repoName: String): Flow<RepoDetailsModel> =
        withContext(Dispatchers.IO) {
            val repoFlow = reposRepository.getRepo(ownerName, repoName)
            val repoEvents = reposRepository.getRepoEvents(ownerName, repoName)

            repoFlow.map { RepoDetailsModel(it, repoEvents.first()) }
        }
}
