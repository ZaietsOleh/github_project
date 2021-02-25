package com.githubuiviewer.datasource.model

import com.google.gson.annotations.SerializedName

data class IssueCommentRepos(
    @SerializedName("id") val id: Int,
    @SerializedName("user") val user: UserResponse,
    @SerializedName("body") val body: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("reactions") val reactions: Reactions
)