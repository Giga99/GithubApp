package com.github.githubapp.domain.models

data class RepoModel(
    val name: String,
    val url: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val repoOwner: RepoOwnerModel
)
