package com.github.githubapp.data.remote.services

import com.github.githubapp.data.remote.responses.RepoEventResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoApiService {

    @GET("repos/{user}/{repo}/events")
    suspend fun getRepoEvents(
        @Path("user") userName: String,
        @Path("repo") repoName: String
    ): Response<List<RepoEventResponse>>
}
