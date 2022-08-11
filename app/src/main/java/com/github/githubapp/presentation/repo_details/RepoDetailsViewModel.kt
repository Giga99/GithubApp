package com.github.githubapp.presentation.repo_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.githubapp.common.BaseViewModel
import com.github.githubapp.common.Destinations
import com.github.githubapp.domain.usecases.GetRepoDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    getRepoDetailsUseCase: GetRepoDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<RepoDetailsViewState, RepoDetailsEvent, RepoDetailsSideEffect>(
    RepoDetailsViewState()
) {

    init {
        val repoOwner =
            savedStateHandle.get<String>(Destinations.RepoDetailsScreen.OWNER_NAME) ?: ""
        val repoName = savedStateHandle.get<String>(Destinations.RepoDetailsScreen.REPO_NAME) ?: ""

        viewModelScope.launch(Dispatchers.IO) {
            getRepoDetailsUseCase(repoOwner, repoName).collect { repoDetailsModel ->
                setState {
                    copy(repoDetailsModel = repoDetailsModel)
                }
            }
        }
    }

    override fun onEvent(event: RepoDetailsEvent) {
        when (event) {
            RepoDetailsEvent.BackButtonClicked -> _sideEffects.trySend(RepoDetailsSideEffect.NavigateBack)
            is RepoDetailsEvent.UrlClicked -> {
                val encodedUrl = URLEncoder.encode(event.url, StandardCharsets.UTF_8.toString())
                _sideEffects.trySend(RepoDetailsSideEffect.NavigateToWebView(encodedUrl))
            }
        }
    }
}
