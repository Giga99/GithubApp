package com.github.githubapp.common

sealed class Destinations(val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route).append('/')
        params.forEach { builder.append("{${it}}/") }
        builder.deleteAt(builder.lastIndex)
        builder.toString()
    }

    object HomeScreen : Destinations("home")

    object RepoDetailsScreen : Destinations("repo", "ownerName", "repoName") {
        const val OWNER_NAME = "ownerName"
        const val REPO_NAME = "repoName"

        operator fun invoke(ownerName: String, repoName: String): String {
            return route.appendParams(
                OWNER_NAME to ownerName,
                REPO_NAME to repoName
            )
        }
    }
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this).append('/')

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("$arg/")
        }
    }
    builder.deleteAt(builder.lastIndex)

    return builder.toString()
}
