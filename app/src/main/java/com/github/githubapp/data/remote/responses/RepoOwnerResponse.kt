package com.github.githubapp.data.remote.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoOwnerResponse(
    @Json(name = "login") val name: String,
    @Json(name = "url") val url: String
)
