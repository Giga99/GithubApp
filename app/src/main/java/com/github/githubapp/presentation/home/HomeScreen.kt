package com.github.githubapp.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.githubapp.R
import com.github.githubapp.common.Destinations
import com.github.githubapp.domain.models.RepoModel

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val viewState = homeViewModel.viewState.collectAsState().value

    LaunchedEffect(homeViewModel.sideEffects) {
        homeViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.NavigateToRepoDetailsScreen -> {
                    navController.navigate(
                        Destinations.RepoDetailsScreen(
                            ownerName = sideEffect.ownerName,
                            repoName = sideEffect.repoName
                        )
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.size_16))
    ) {
        Text(
            text = stringResource(R.string.users_repos),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_32)))
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(viewState.reposList) {
                RepoRow(it) {
                    homeViewModel.onEvent(HomeEvent.RepoClicked(it))
                }
            }
        }
    }
}

@Composable
private fun RepoRow(
    repoModel: RepoModel,
    onRepoClicked: (RepoModel) -> Unit
) {
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_8)))
    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.size_16)),
        elevation = dimensionResource(R.dimen.size_8),
        modifier = Modifier.clickable { onRepoClicked(repoModel) }
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.size_8))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = repoModel.name,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = repoModel.repoOwner.name,
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_8)))
            Text(text = repoModel.url)
        }
    }
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_8)))
}
