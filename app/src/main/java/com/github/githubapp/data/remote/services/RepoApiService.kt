package com.github.githubapp.data.remote.services

import com.github.githubapp.data.remote.responses.RepoEventResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoApiService {

    @GET("repos/{user}/{repo}/events")
    fun getRepoEvents(
        @Path("user") userName: String,
        @Path("repo") repoName: String
    ): List<RepoEventResponse>
}
