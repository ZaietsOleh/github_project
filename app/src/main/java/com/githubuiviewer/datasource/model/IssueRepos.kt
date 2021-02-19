package com.githubuiviewer.datasource.model

import com.google.gson.annotations.SerializedName

data class IssueRepos(
    @SerializedName("number") val number: Int,
    @SerializedName("title") val title: String,
    @SerializedName("user") val user: UserResponse,
    @SerializedName("comments") val comments: Int
)
