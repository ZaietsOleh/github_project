package com.githubuiviewer.datasource.api

import com.githubuiviewer.datasource.model.*
import retrofit2.http.*

interface GitHubService {

    //todo ask is it ok hardcode url?
    @Headers("Accept: application/json")
    @POST("https://github.com/login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
    ): AccessTokenResponse

    @GET("/user")
    suspend fun getUserByToken(@Header("Authorization") auth: String): UserResponse

    @GET("/users/{user}")
    suspend fun getUserByNickname(@Path("user") user: String): UserResponse

    @GET("/user/repos")
    suspend fun getReposByToken(
        @Header("Authorization") auth: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): List<ReposResponse>

    @GET("/users/{owner}/repos")
    suspend fun getReposByNickname(@Path("owner") owner: String): List<ReposResponse>

    @GET("/repos/{owner}/{repo}/contributors")
    suspend fun getContributors(
        @Header("Authorization") auth: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int,
        @Path("repo") repo: String,
        @Path("owner") owner: String
    ): List<UserResponse>

    @GET("/repos/{owner}/{repo}/issues")
    suspend fun getIssues(
        @Header("Authorization") auth: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int,
        @Path("repo") repo: String,
        @Path("owner") owner: String
    ): List<IssueRepos>

    @GET("/repos/{owner}/{repo}/issues/{issue_number}")
    suspend fun getIssueDetail(
        @Header("Authorization") auth: String,
        @Path("repo") repo: String,
        @Path("owner") owner: String,
        @Path("issue_number") issue_number: Int
    ): IssueDetailRepos

    @GET("/repos/{owner}/{repo}/issues/{issue_number}/comments")
    suspend fun getIssueComments(
        @Header("Authorization") auth: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int,
        @Path("repo") repo: String,
        @Path("owner") owner: String,
        @Path("issue_number") issue_number: String
    ): List<IssueCommentRepos>

    @GET("/{owner}/{repo}/master/README.md")
    suspend fun getReadme(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): String

    @POST("/repos/{owner}/{repo}/issues/comments/{comment_id}/reactions")
    suspend fun createReactionForIssueComment(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("comment_id") comment_id: Int,
        @Body reaction: String
    )

    @GET("search/users")
    suspend fun getSearcher(@Query("q") q: String): SearchResponse
}