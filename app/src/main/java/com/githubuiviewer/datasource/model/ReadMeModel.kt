package com.githubuiviewer.datasource.model

import com.google.gson.annotations.SerializedName

data class ReadMeModel(
    @SerializedName("content")
    val content: String
)