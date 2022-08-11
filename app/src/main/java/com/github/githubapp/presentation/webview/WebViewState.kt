package com.github.githubapp.presentation.webview

sealed class WebViewEvent {
    object BackButtonClicked : WebViewEvent()
}

sealed class WebViewSideEffect {
    object NavigateBack : WebViewSideEffect()
}

data class WebViewViewState(
    val url: String = ""
)
