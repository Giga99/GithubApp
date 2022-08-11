package com.github.githubapp.presentation.repo_details

import com.github.githubapp.domain.models.RepoDetailsModel

sealed class RepoDetailsEvent {
    object BackButtonClicked : RepoDetailsEvent()
    data class UrlClicked(val url: String) : RepoDetailsEvent()
}

sealed class RepoDetailsSideEffect {
    object NavigateBack : RepoDetailsSideEffect()
    data class NavigateToWebView(val url: String) : RepoDetailsSideEffect()
}

data class RepoDetailsViewState(
    val repoDetailsModel: RepoDetailsModel? = null
)
