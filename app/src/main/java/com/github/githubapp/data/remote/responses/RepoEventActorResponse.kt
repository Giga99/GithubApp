package com.github.githubapp.data.remote.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoEventActorResponse(
    @Json(name = "display_login") val displayName: String,
    @Json(name = "url") val url: String
)
