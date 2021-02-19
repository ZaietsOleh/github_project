package com.githubuiviewer.datasource.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("login") val name: String,
    @SerializedName("avatar_url") val avatar_url: String
)