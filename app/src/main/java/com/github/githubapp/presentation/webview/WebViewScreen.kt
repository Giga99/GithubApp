package com.github.githubapp.presentation.webview

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.githubapp.R

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    navController: NavController,
    webViewViewModel: WebViewViewModel = hiltViewModel()
) {
    val viewState = webViewViewModel.viewState.collectAsState().value

    LaunchedEffect(webViewViewModel.sideEffects) {
        webViewViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                WebViewSideEffect.NavigateBack -> navController.navigateUp()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.size_16))
    ) {
        IconButton(
            modifier = Modifier.size(dimensionResource(R.dimen.size_32)),
            onClick = { webViewViewModel.onEvent(WebViewEvent.BackButtonClicked) }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = stringResource(R.string.back_button_description)
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_32)))
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                    }
                },
                update = { webView ->
                    webView.loadUrl(viewState.url)
                }
            )
        }
    }
}
