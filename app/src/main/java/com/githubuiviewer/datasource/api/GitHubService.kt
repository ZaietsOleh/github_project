package com.githubuiviewer.datasource.api

import com.githubuiviewer.datasource.model.*
import retrofit2.http.*

interface GitHubService {

    @Headers("Accept: application/json")
    @POST("https://github.com/login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
    ): AccessTokenResponse

    @GET("/user")
    suspend fun getUserByToken(): UserResponse

    @GET("/users/{user}")
    suspend fun getUserByNickname(@Path("user") user: String): UserResponse

    @GET("/user/repos")
    suspend fun getReposByToken(
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): List<ReposResponse>

    @GET("/users/{owner}/repos")
    suspend fun getReposByNickname(
        @Path("owner") owner: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): List<ReposResponse>

    @GET("https://raw.githubusercontent.com/{owner}/{repo}/master/README.md")
    suspend fun getReadme(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): ReadMeModel

    @GET("/repos/{owner}/{repo}/contributors")
    suspend fun getContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int,
    ): List<UserResponse>

    @GET("/repos/{owner}/{repo}/issues")
    suspend fun getIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): List<IssueRepos>

    @GET("/repos/{owner}/{repo}/issues/{issue_number}")
    suspend fun getIssueDetail(
        @Path("repo") repo: String,
        @Path("owner") owner: String,
        @Path("issue_number") issue_number: Int
    ): IssueDetailRepos

    @GET("/repos/{owner}/{repo}/issues/{issue_number}/comments")
    suspend fun getIssueComments(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("issue_number") issue_number: Int,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): List<IssueCommentRepos>

    @POST("/repos/{owner}/{repo}/issues/comments/{comment_id}/reactions")
    suspend fun createReactionForIssueComment(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("comment_id") comment_id: Int,
        @Body reaction: String
    )

    @GET("search/users")
    suspend fun getSearcher(
        @Query("q") q: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): SearchResponse
}