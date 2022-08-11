package com.github.githubapp.domain.mappers

import com.github.githubapp.data.remote.responses.RepoEventActorResponse
import com.github.githubapp.data.remote.responses.RepoEventResponse
import com.github.githubapp.domain.models.RepoEventActorModel
import com.github.githubapp.domain.models.RepoEventModel

fun RepoEventResponse.toModel(): RepoEventModel =
    RepoEventModel(
        id = id,
        type = type,
        eventActor = repoEventActor.toModel()
    )

fun RepoEventActorResponse.toModel(): RepoEventActorModel =
    RepoEventActorModel(
        displayName = displayName,
        url = url
    )
