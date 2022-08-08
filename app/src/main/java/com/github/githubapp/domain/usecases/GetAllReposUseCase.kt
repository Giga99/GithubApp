package com.github.githubapp.domain.usecases

import com.github.githubapp.domain.models.RepoModel
import com.github.githubapp.domain.repos.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllReposUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {

    suspend operator fun invoke(): Flow<List<RepoModel>> = withContext(Dispatchers.IO) {
        val jake = "JakeWharton"
        val infinum = "infinum"
        usersRepository.fetchUserRepositories(jake)
        usersRepository.fetchUserRepositories(infinum)

        val jakeReposFlow = usersRepository.getUserRepositories(jake)
        val infinumReposFlow = usersRepository.getUserRepositories(infinum)

        jakeReposFlow.combine(infinumReposFlow) { jakeRepos, infinumRepos ->
            jakeRepos.plus(infinumRepos)
        }
    }
}
