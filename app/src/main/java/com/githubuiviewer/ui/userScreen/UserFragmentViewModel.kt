package com.githubuiviewer.ui.userScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githubuiviewer.R
import com.githubuiviewer.datasource.api.*
import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.tools.ResourceRepository
import com.githubuiviewer.tools.State
import com.githubuiviewer.tools.UserProfile
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserFragmentViewModel @Inject constructor(
        private val profileRepository: ProfileRepository,
        private val resourceRepository: ResourceRepository
) : ViewModel() {
    lateinit var userProfile: UserProfile

    private val _userInfoLiveData = MutableLiveData<State<UserResponse, String>>()
    val userInfoLiveData: LiveData<State<UserResponse, String>> = _userInfoLiveData

    private val _reposLiveData = MutableLiveData<State<List<ReposResponse>, String>>()
    val reposLiveData: LiveData<State<List<ReposResponse>, String>> = _reposLiveData

    fun getContent() {
        viewModelScope.launch {
            try {
                _userInfoLiveData.value = State.Content(profileRepository.getUser(userProfile))
                _reposLiveData.value = State.Content(profileRepository.getRepos(userProfile))
            } catch (unauthorizedException: UnauthorizedException) {
                _userInfoLiveData.value = State.Error(resourceRepository.getString(R.string.error_user_loading))
            } catch (notFoundException: NotFoundException) {
                _userInfoLiveData.value = State.Error(resourceRepository.getString(R.string.error_user_loading))
            } catch (dataLoadingException: DataLoadingException) {
                _userInfoLiveData.value = State.Error(resourceRepository.getString(R.string.error_user_loading))
            }
        }
    }
}