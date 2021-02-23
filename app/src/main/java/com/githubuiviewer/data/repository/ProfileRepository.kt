package com.githubuiviewer.data.repository

import android.util.Log
import com.githubuiviewer.datasource.api.GitHubService
import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.tools.sharedPrefsTools.SharedPref
import com.githubuiviewer.tools.UserProfile
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val gitHubService: GitHubService,
    private val sharedPref: SharedPref
) {
    /*init {
        sharedPref.token = "bearer 5708a3a933119e20d1020161f6ed24b4a21ea9a7"
    }*/

    suspend fun getUser(userProfile: UserProfile) : UserResponse {
        return when (userProfile) {
            is UserProfile.AuthorizedUser -> gitHubService.getUserByToken(sharedPref.token)
            is UserProfile.PublicUser -> gitHubService.getUserByNickname(userProfile.userNickname)
        }
    }

    suspend fun getRepos(userProfile: UserProfile) : List<ReposResponse> {
        return when (userProfile) {
            is UserProfile.AuthorizedUser -> gitHubService.getReposByToken(sharedPref.token)
            is UserProfile.PublicUser -> gitHubService.getReposByNickname(userProfile.userNickname)
        }
    }
}