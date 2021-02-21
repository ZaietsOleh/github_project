package com.githubuiviewer.datasource.api

import com.githubuiviewer.datasource.model.*
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

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}/issues")
    suspend fun getIssues(@Header("Authorization") auth: String, @Path("repo") repo: String, @Path("owner") owner: String): List<IssueRepos>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}/issues")
    suspend fun getIssueDetail(@Header("Authorization") auth: String, @Path("repo") repo: String, @Path("owner") owner: String): List<IssueRepos>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}/issues/{issue_number}/comments")
    suspend fun getIssueComments(@Header("Authorization") auth: String, @Path("repo") repo: String, @Path("owner") owner: String, @Path("issue_number") issue_number: String): List<IssueCommentRepos>

    //raw.githubusercontent.com
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/{owner}/{repo}/master/README.md")
    suspend fun getReadme(@Header("Authorization") auth: String, @Path("owner") owner: String, @Path("repo") repo: String, ): String
}