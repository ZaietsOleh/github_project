package com.githubuiviewer.datasource.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("items") val items: List<UserResponse>
)