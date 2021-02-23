package com.githubuiviewer.data.repository

import android.util.Log
import com.githubuiviewer.tools.MAIN_DEBUG_TAG
import com.githubuiviewer.tools.clientId
import com.githubuiviewer.tools.clientSecret
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
        Log.d(MAIN_DEBUG_TAG,"LogInRepository download new token  $token")
        sharedPref.token = token
    }

    private suspend fun getAccessToken(code: String): AccessTokenResponse {
        //todo clean up
        Thread.sleep(5_000L)
        Log.d(MAIN_DEBUG_TAG, "getAccessToken go to internet")
        return gitHubService.getAccessToken(clientId, clientSecret, code)
    }

}