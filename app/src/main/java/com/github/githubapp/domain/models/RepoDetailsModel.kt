package com.github.githubapp.domain.models

data class RepoDetailsModel(
    private val repoModel: RepoModel,
    private val repoEventModel: RepoEventModel?
)
