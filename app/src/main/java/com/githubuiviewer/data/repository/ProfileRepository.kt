package com.githubuiviewer.data.repository

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
//    init {
//        sharedPref.token = "bearer 83e23a9d48ef39921212a26e309e642ef4574de1"
//    }

    suspend fun getUser(userProfile: UserProfile) : UserResponse {
        return when (userProfile) {
            is UserProfile.AuthorizedUser -> gitHubService.getUserByToken(sharedPref.token)
            is UserProfile.PublicUser -> gitHubService.getUserByNickname(userProfile.userNickname)
        }
    }

    suspend fun getRepos(userProfile: UserProfile, currentPage: Int) : List<ReposResponse> {
        return when (userProfile) {
            is UserProfile.AuthorizedUser -> gitHubService.getReposByToken(sharedPref.token, 40, currentPage)
            is UserProfile.PublicUser -> gitHubService.getReposByNickname(userProfile.userNickname, 40, currentPage)
        }
    }
}