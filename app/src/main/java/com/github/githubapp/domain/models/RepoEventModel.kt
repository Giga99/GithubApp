package com.github.githubapp.domain.models

import com.github.githubapp.data.common.enums.RepoEventTypeEnum

data class RepoEventModel(
    val id: String,
    val type: RepoEventTypeEnum,
    val eventActor: RepoEventActorModel
)
