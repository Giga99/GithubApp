package com.github.githubapp.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.githubapp.R
import com.github.githubapp.common.Destinations
import com.github.githubapp.common.Result
import com.github.githubapp.domain.models.RepoModel
import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val viewState = homeViewModel.viewState.collectAsState().value
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

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
        HorizontalTabs(
            items = viewState.tabs,
            pagerState = pagerState,
            scope = coroutineScope
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_16)))
        HorizontalPager(
            count = viewState.reposList.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { currentPage ->
            ReposPage(reposList = viewState.reposList[currentPage]) {
                homeViewModel.onEvent(HomeEvent.RepoClicked(it))
            }
        }

    }
}

@Composable
private fun ColumnScope.ReposPage(
    reposList: Result<List<RepoModel>>,
    onRepoClicked: (RepoModel) -> Unit
) {
    when (reposList) {
        is Result.Success -> {
            HomeScreenRepos(
                repos = reposList.data,
                onRepoClicked = onRepoClicked
            )
        }
        is Result.Error -> {
            HomeScreenReposError(message = reposList.message ?: "")
        }
        is Result.Loading -> {
            HomeScreenReposLoading()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalTabs(
    items: List<String>,
    pagerState: PagerState,
    scope: CoroutineScope
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
        backgroundColor = MaterialTheme.colors.background
    ) {
        items.forEachIndexed { index, item ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(page = index)
                    }
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.primaryVariant
            ) {
                Text(text = item)
            }
        }
    }
}

@Composable
private fun ColumnScope.HomeScreenRepos(
    repos: List<RepoModel>?,
    onRepoClicked: (RepoModel) -> Unit
) {
    if (repos == null || repos.isEmpty()) {
        Text(
            text = stringResource(R.string.no_repos),
            style = MaterialTheme.typography.h6
        )
    } else {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(repos) {
                RepoRow(
                    repoModel = it,
                    onRepoClicked = onRepoClicked
                )
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

@Composable
private fun HomeScreenReposError(
    message: String
) {
    Text(
        text = message,
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.error
    )
}

@Composable
private fun ColumnScope.HomeScreenReposLoading() {
    CircularProgressIndicator(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.size_16))
            .align(Alignment.CenterHorizontally)
    )
}
