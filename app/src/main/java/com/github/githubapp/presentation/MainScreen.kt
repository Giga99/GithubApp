package com.github.githubapp.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.githubapp.common.Destinations
import com.github.githubapp.presentation.home.HomeScreen
import com.github.githubapp.presentation.ui.theme.GithubAppTheme

@Composable
fun MainScreen() {
    GithubAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Destinations.HomeScreen.fullRoute
            ) {
                composable(route = Destinations.HomeScreen.fullRoute) {
                    HomeScreen(navController = navController)
                }

                composable(route = Destinations.RepoDetailsScreen.fullRoute) {

                }
            }
        }
    }
}