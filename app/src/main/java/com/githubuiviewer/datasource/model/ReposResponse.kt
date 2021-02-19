package com.githubuiviewer.datasource.model

import com.google.gson.annotations.SerializedName

data class ReposResponse (
    @SerializedName("name") val name: String,
)