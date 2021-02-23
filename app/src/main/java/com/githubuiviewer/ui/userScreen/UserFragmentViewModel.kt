package com.githubuiviewer.ui.userScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githubuiviewer.R
import com.githubuiviewer.DATA_NOT_FOUND
import com.githubuiviewer.datasource.api.*
import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.tools.ResourceRepository
import com.githubuiviewer.tools.State
import com.githubuiviewer.tools.UserProfile
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserFragmentViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val resourceRepository: ResourceRepository,
    private val gitHubService: GitHubService
) : ViewModel() {
    lateinit var userProfile: UserProfile

    private val _userInfoLiveData = MutableLiveData<State<UserResponse, String>>()
    val userInfoLiveData: LiveData<State<UserResponse, String>> = _userInfoLiveData

    private val _reposLiveData = MutableLiveData<State<List<ReposResponse>, String>>()
    val reposLiveData: LiveData<State<List<ReposResponse>, String>> = _reposLiveData

    init {
        _userInfoLiveData.value = State.Loading
        _reposLiveData.value = State.Loading
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is UnauthorizedException -> _userInfoLiveData.value = State.Unauthorized
            is NotFoundException ->
                setErrorToLiveData(resourceRepository.getString(R.string.error_not_found))
            is DataLoadingException ->
                setErrorToLiveData(resourceRepository.getString(R.string.error_data_loading))
        }
    }

    private fun setErrorToLiveData(textError: String) {
        if (_userInfoLiveData.value is State.Loading) {
            _userInfoLiveData.value = State.Error(DATA_NOT_FOUND)
            _reposLiveData.value = State.Error(DATA_NOT_FOUND)
        } else {
            _reposLiveData.value =
                State.Error(textError)
        }
    }

    fun getContent() {
        viewModelScope.launch(exceptionHandler) {
            launch {
                _userInfoLiveData.value = State.Content(profileRepository.getUser(userProfile))
                _reposLiveData.value = State.Content(profileRepository.getRepos(userProfile))
            }
        }
    }
}