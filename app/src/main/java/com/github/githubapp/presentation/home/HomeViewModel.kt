package com.github.githubapp.presentation.home

import androidx.lifecycle.viewModelScope
import com.github.githubapp.common.BaseViewModel
import com.github.githubapp.domain.usecases.GetAllReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllReposUseCase: GetAllReposUseCase,
) : BaseViewModel<HomeViewState, HomeEvent, HomeSideEffect>(HomeViewState()) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllReposUseCase().collect { repos ->
                setState { copy(reposList = repos) }
            }
        }
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.RepoClicked -> _sideEffects.trySend(
                HomeSideEffect.NavigateToRepoDetailsScreen(
                    event.repoModel.repoOwner.name,
                    event.repoModel.name
                )
            )
        }
    }
}
