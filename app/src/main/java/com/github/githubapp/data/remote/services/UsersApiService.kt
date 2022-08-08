package com.github.githubapp.data.remote.services

import com.github.githubapp.data.remote.responses.RepoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersApiService {

    @GET("users/{user}/repos")
    suspend fun getUserRepos(@Path("user") userName: String): Response<List<RepoResponse>>
}
