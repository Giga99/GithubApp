package com.github.githubapp.data.remote.responses

import com.github.githubapp.data.common.enums.RepoEventTypeEnum
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoEventResponse(
    @Json(name = "type") val type: RepoEventTypeEnum,
    @Json(name = "actor") val repoEventActor: RepoEventActorResponse
)
