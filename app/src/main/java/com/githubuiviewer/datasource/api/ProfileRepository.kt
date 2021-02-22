package com.githubuiviewer.datasource.api

import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.tools.sharedPrefsTools.SharedPref
import com.githubuiviewer.tools.UserProfile
import javax.inject.Inject

class ProfileRepository @Inject constructor(
        private val gitHubService: GitHubService,
        private val sharedPref: SharedPref
) {
    init {
        sharedPref.token = "bearer 8d68a427bd4b6bc95025158a0c654e57719f99de"
    }

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