package com.githubuiviewer.datasource.api

import com.githubuiviewer.datasource.model.AccessTokenResponse
import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.UserResponse
import retrofit2.http.*

interface GitHubService {
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String,
            @Field("code") code: String,
    ): AccessTokenResponse

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user")
    suspend fun getUser(@Header("Authorization") auth: String): UserResponse

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user/repos")
    suspend fun getRepos(@Header("Authorization") auth: String): List<ReposResponse>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}/contributors")
    suspend fun getContributors(@Header("Authorization") auth: String, @Path("repo") repo: String, @Path("owner") owner: String): List<UserResponse>
}