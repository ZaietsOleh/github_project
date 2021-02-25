package com.githubuiviewer.data.repository

import android.util.Log
import com.githubuiviewer.datasource.api.GitHubService
import com.githubuiviewer.datasource.model.AccessTokenResponse
import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.tools.*
import com.githubuiviewer.tools.sharedPrefsTools.SharedPref
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    private val gitHubService: GitHubService,
    private val sharedPref: SharedPref
) {

    suspend fun updateToken(code: String){
        Log.d(MAIN_DEBUG_TAG,"LogInRepository start updateToken")
        val response = getAccessToken(code)
        val token = "${response.tokenType} ${response.accessToken}"
        Log.d(MAIN_DEBUG_TAG,"LogInRepository download new token  $token")
        sharedPref.token = token
    }

    private suspend fun getAccessToken(code: String): AccessTokenResponse {
        Log.d(MAIN_DEBUG_TAG, "getAccessToken go to internet")
        return gitHubService.getAccessToken(clientId, clientSecret, code)
    }

    suspend fun getUser(userProfile: UserProfile) : UserResponse {
        return when (userProfile) {
            is UserProfile.AuthorizedUser -> gitHubService.getUserByToken()
            is UserProfile.PublicUser -> gitHubService.getUserByNickname(userProfile.userNickname)
        }
    }

    suspend fun getRepos(userProfile: UserProfile, currentPage: Int) : List<ReposResponse> {
        return when (userProfile) {
            is UserProfile.AuthorizedUser -> gitHubService.getReposByToken(PER_PAGE, currentPage)
            is UserProfile.PublicUser -> gitHubService.getReposByNickname(userProfile.userNickname, PER_PAGE, currentPage)
        }
    }
}