package com.githubuiviewer.datasource.model

import com.google.gson.annotations.SerializedName

data class Reactions(
    @SerializedName("total_count") val total_count: Int,
    @SerializedName("+1") val like: Int,
    @SerializedName("-1") val dislike: Int,
    @SerializedName("laugh") val laugh: Int,
    @SerializedName("hooray") val hooray: Int,
    @SerializedName("confused") val confused: Int,
    @SerializedName("heart") val heart: Int,
    @SerializedName("rocket") val rocket: Int,
    @SerializedName("eyes") val eyes: Int,
)
