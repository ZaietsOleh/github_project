package com.githubuiviewer.datasource.model

import com.google.gson.annotations.SerializedName

data class ReactionResponse (
    @SerializedName("user") val user: UserResponse
    )