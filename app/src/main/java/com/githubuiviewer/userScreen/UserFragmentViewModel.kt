package com.githubuiviewer.userScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githubuiviewer.datasource.api.GitHubService
import com.githubuiviewer.sharedPrefsTools.SharedPref
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserFragmentViewModel @Inject constructor(
    private val gitHubService: GitHubService,
    private val sharedPref: SharedPref,
) : ViewModel() {

    fun getUserInfo(){
        /*viewModelScope.launch {
            try {
                gitHubService.getUser(sharedPref.token)
            }catch (e : Exception){

            }
        }*/
    }

    fun getUserRepos(){
        viewModelScope.launch {

        }
    }
}