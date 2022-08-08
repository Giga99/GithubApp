package com.github.githubapp.domain.mappers

import com.github.githubapp.data.local.entities.RepoEntity
import com.github.githubapp.data.local.entities.RepoEntity.RepoOwnerEntity
import com.github.githubapp.data.remote.responses.RepoOwnerResponse
import com.github.githubapp.data.remote.responses.RepoResponse
import com.github.githubapp.domain.models.RepoModel
import com.github.githubapp.domain.models.RepoOwnerModel

fun RepoResponse.toEntity(): RepoEntity =
    RepoEntity(
        id = id,
        name = name,
        url = url,
        repoOwner = repoOwner.toEntity()
    )

fun RepoOwnerResponse.toEntity(): RepoOwnerEntity =
    RepoOwnerEntity(
        name = name,
        url = url
    )

fun RepoEntity.toModel(): RepoModel =
    RepoModel(
        name = name,
        url = url,
        repoOwner = repoOwner.toModel()
    )

fun RepoOwnerEntity.toModel(): RepoOwnerModel =
    RepoOwnerModel(
        name = name,
        url = url
    )
