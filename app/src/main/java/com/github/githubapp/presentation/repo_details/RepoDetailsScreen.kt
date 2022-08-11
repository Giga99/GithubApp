package com.github.githubapp.presentation.repo_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.githubapp.R
import com.github.githubapp.common.Destinations
import com.github.githubapp.common.Result
import com.github.githubapp.domain.models.RepoDetailsModel
import com.github.githubapp.domain.models.RepoEventActorModel
import com.github.githubapp.domain.models.RepoEventModel
import com.github.githubapp.domain.models.RepoOwnerModel

@Composable
fun RepoDetailsScreen(
    navController: NavController,
    repoDetailsViewModel: RepoDetailsViewModel = hiltViewModel()
) {
    val viewState = repoDetailsViewModel.viewState.collectAsState().value

    LaunchedEffect(repoDetailsViewModel.sideEffects) {
        repoDetailsViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                RepoDetailsSideEffect.NavigateBack -> navController.navigateUp()
                is RepoDetailsSideEffect.NavigateToWebView -> navController.navigate(
                    Destinations.WebViewScreen(
                        sideEffect.url
                    )
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.size_16))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier.size(dimensionResource(R.dimen.size_32)),
                onClick = { repoDetailsViewModel.onEvent(RepoDetailsEvent.BackButtonClicked) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = stringResource(R.string.back_button_description)
                )
            }
            Text(
                text = stringResource(R.string.repo_title),
                style = MaterialTheme.typography.body1
            )
            Box(modifier = Modifier.width(0.dp))
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_32)))
        when (viewState.repoDetailsModel) {
            is Result.Success -> {
                RepoDetails(repoDetails = viewState.repoDetailsModel.data) {
                    repoDetailsViewModel.onEvent(RepoDetailsEvent.UrlClicked(it))
                }
            }
            is Result.Error -> {
                RepoDetailsError(message = viewState.repoDetailsModel.message ?: "")
            }
            is Result.Loading -> {
                RepoDetailsLoading()
            }
        }
    }
}

@Composable
fun RepoDetails(
    repoDetails: RepoDetailsModel?,
    onUrlClicked: (String) -> Unit
) {
    if (repoDetails == null) {
        Text(
            text = stringResource(R.string.no_data_for_repo),
            style = MaterialTheme.typography.h6
        )
    } else {
        Text(
            text = repoDetails.repoModel.name,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_32)))
        Text(
            text = repoDetails.repoModel.url,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.clickable { onUrlClicked(repoDetails.repoModel.url) }
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_32)))
        RepoOwner(repoDetails.repoModel.repoOwner) { url ->
            onUrlClicked(url)
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_32)))
        LastEvent(repoEventModel = repoDetails.repoEventModel) { url ->
            onUrlClicked(url)
        }
    }
}

@Composable
fun RepoOwner(
    repoOwner: RepoOwnerModel,
    urlClicked: (String) -> Unit
) {
    Text(
        text = stringResource(R.string.owner),
        style = MaterialTheme.typography.h6
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_8)))
    Text(
        text = repoOwner.name,
        style = MaterialTheme.typography.body1,
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_4)))
    Text(
        text = repoOwner.url,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.clickable { urlClicked(repoOwner.url) }
    )
}

@Composable
fun LastEvent(
    repoEventModel: RepoEventModel?,
    urlClicked: (String) -> Unit
) {
    Text(
        text = stringResource(R.string.last_event),
        style = MaterialTheme.typography.h5
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_8)))
    if (repoEventModel == null) {
        Text(
            text = stringResource(R.string.no_events),
            style = MaterialTheme.typography.body1
        )
    } else {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.event_type),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.size_2)))
            Text(
                text = stringResource(repoEventModel.type.stringRes),
                style = MaterialTheme.typography.body1
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_8)))
        LastEventActor(
            repoEventActorModel = repoEventModel.eventActor,
            urlClicked = urlClicked
        )
    }
}

@Composable
fun LastEventActor(
    repoEventActorModel: RepoEventActorModel,
    urlClicked: (String) -> Unit
) {
    Text(
        text = stringResource(R.string.last_event_actor),
        style = MaterialTheme.typography.h6
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_8)))
    Text(
        text = repoEventActorModel.displayName,
        style = MaterialTheme.typography.body1,
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_4)))
    Text(
        text = repoEventActorModel.url,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.clickable { urlClicked(repoEventActorModel.url) }
    )
}

@Composable
private fun RepoDetailsError(
    message: String
) {
    Text(
        text = message,
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.error
    )
}

@Composable
private fun ColumnScope.RepoDetailsLoading() {
    CircularProgressIndicator(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.size_16))
            .align(Alignment.CenterHorizontally)
    )
}
