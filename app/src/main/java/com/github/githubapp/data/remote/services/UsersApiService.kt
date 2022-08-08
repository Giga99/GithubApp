package com.github.githubapp.data.remote.services

import com.github.githubapp.data.remote.responses.RepoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersApiService {

    @GET("users/{user}/repos")
    fun getUserRepos(@Path("user") userName: String): List<RepoResponse>
}
