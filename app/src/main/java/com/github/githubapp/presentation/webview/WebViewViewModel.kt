package com.github.githubapp.presentation.webview

import androidx.lifecycle.SavedStateHandle
import com.github.githubapp.common.BaseViewModel
import com.github.githubapp.common.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<WebViewViewState, WebViewEvent, WebViewSideEffect>(WebViewViewState()) {

    init {
        val url = savedStateHandle.get<String>(Destinations.WebViewScreen.URL) ?: ""
        setState { copy(url = url) }
    }

    override fun onEvent(event: WebViewEvent) {
        when (event) {
            WebViewEvent.BackButtonClicked -> _sideEffects.trySend(WebViewSideEffect.NavigateBack)
        }
    }
}
