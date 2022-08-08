package com.github.githubapp.data.remote.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String,
    @Json(name = "owner") val repoOwner: RepoOwnerResponse
)
