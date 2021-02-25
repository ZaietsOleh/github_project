package com.githubuiviewer.datasource.model

import com.google.gson.annotations.SerializedName

data class IssueDetailRepos(
    @SerializedName("number") val number: Int,
    @SerializedName("user") val user: UserResponse,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("reactions") val reactions: Reactions
)
