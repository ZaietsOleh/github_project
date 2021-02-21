package com.githubuiviewer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.githubuiviewer.sharedPrefsTools.SharedPref
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val sharedPreferences: SharedPref

) : ViewModel() {

    //todo think about authorize
    private val _reposLiveData = MutableLiveData<AuthorizeStatus>()

    val reposLiveData: LiveData<AuthorizeStatus> = _reposLiveData

    fun checkAuthorized() {
        sharedPreferences.token
    }
}

enum class AuthorizeStatus {
    UserAlreadyAuthorized,
    UserNotAuthorized
}