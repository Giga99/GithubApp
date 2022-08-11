package com.github.githubapp.presentation.repo_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
        viewState.repoDetailsModel?.let { repoDetails ->
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
                modifier = Modifier.clickable {
                    repoDetailsViewModel.onEvent(RepoDetailsEvent.UrlClicked(repoDetails.repoModel.url))
                }
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_32)))
            RepoOwner(repoDetails.repoModel.repoOwner) { url ->
                repoDetailsViewModel.onEvent(RepoDetailsEvent.UrlClicked(url))
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_32)))
            LastEvent(repoEventModel = repoDetails.repoEventModel) { url ->
                repoDetailsViewModel.onEvent(RepoDetailsEvent.UrlClicked(url))
            }
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
