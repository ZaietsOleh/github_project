package com.githubuiviewer.ui.userScreen.adapter

import com.githubuiviewer.datasource.model.ReposResponse

sealed class ReposRecyclerState {
    data class Error(val errorText: String) : ReposRecyclerState()
    data class Content(val data: List<ReposResponse>) : ReposRecyclerState()
}