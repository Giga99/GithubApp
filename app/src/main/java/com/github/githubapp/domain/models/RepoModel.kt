package com.github.githubapp.domain.models

data class RepoModel(
    val name: String,
    val url: String,
    val repoOwner: RepoOwnerModel
)
