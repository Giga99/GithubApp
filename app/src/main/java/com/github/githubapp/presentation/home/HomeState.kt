package com.github.githubapp.presentation.home

import com.github.githubapp.common.Result
import com.github.githubapp.domain.models.RepoModel

sealed class HomeEvent {
    data class RepoClicked(val repoModel: RepoModel) : HomeEvent()
}

sealed class HomeSideEffect {
    data class NavigateToRepoDetailsScreen(val ownerName: String, val repoName: String) :
        HomeSideEffect()
}

data class HomeViewState(
    val reposList: List<Result<List<RepoModel>>> = listOf(
        Result.Loading(),
        Result.Loading(),
        Result.Loading()
    ),
    val tabs: List<String> = listOf("All repos", "Jake's repos", "Infinum's repos")
)
