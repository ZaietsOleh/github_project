package com.githubuiviewer.data.repository

import android.util.Log
import com.githubuiviewer.MAIN_DEBUG_TAG
import com.githubuiviewer.clientId
import com.githubuiviewer.clientSecret
import com.githubuiviewer.datasource.api.GitHubService
import com.githubuiviewer.datasource.model.AccessTokenResponse
import com.githubuiviewer.tools.sharedPrefsTools.SharedPref
import javax.inject.Inject

class LogInRepository @Inject constructor(
    private val gitHubService: GitHubService,
    private val sharedPref: SharedPref
) {

    suspend fun updateToken(code: String){
        Log.d(MAIN_DEBUG_TAG,"LogInRepository start updateToken")
        val response = getAccessToken(code)
        val token = "${response.tokenType} ${response.accessToken}"
        Log.d(MAIN_DEBUG_TAG,"LogInRepository start updateToken new token is $token")
        sharedPref.token = token
    }

    private suspend fun getAccessToken(code: String): AccessTokenResponse {
        return gitHubService.getAccessToken(clientId, clientSecret, code)
    }

}