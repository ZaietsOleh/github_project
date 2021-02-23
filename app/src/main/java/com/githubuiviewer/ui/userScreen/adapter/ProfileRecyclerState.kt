package com.githubuiviewer.ui.userScreen.adapter

import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.UserResponse

sealed class ProfileRecyclerState {
    data class Error(val errorText: String) : ProfileRecyclerState()
    data class Repos(val repos: ReposResponse) : ProfileRecyclerState()
    data class User(val user: UserResponse) : ProfileRecyclerState()
}