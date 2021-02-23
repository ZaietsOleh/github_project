package com.githubuiviewer.ui.userScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githubuiviewer.tools.MAIN_DEBUG_TAG
import com.githubuiviewer.R
import com.githubuiviewer.tools.USER_NOT_FOUND
import com.githubuiviewer.data.repository.ProfileRepository
import com.githubuiviewer.datasource.api.*
import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.data.repository.ResourceRepository
import com.githubuiviewer.tools.State
import com.githubuiviewer.tools.UserProfile
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
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

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        when (throwable) {
            is UnauthorizedException -> _userInfoLiveData.value = State.Error(resourceRepository.getString(R.string.error_user_loading))

            is NotFoundException -> {
                if (_userInfoLiveData.value == null) {
                    _userInfoLiveData.value = State.Error(USER_NOT_FOUND)
                } else {
                    _reposLiveData.value =
                        State.Error(resourceRepository.getString(R.string.error_not_found))
                }
            }

            is DataLoadingException -> {
                if (_userInfoLiveData.value == null) {
                    _userInfoLiveData.value = State.Error(USER_NOT_FOUND)
                } else {
                    _reposLiveData.value =
                        State.Error(resourceRepository.getString(R.string.error_data_loading))
                }
            }
        }
    }

    fun getContent() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d(MAIN_DEBUG_TAG, "get content start try")
                _userInfoLiveData.value = State.Content(profileRepository.getUser(userProfile))
                //_userInfoLiveData.value = State.Content(profileRepository.getUser(UserProfile.PublicUser("ZGoblin")))
                _reposLiveData.value = State.Content(profileRepository.getRepos(userProfile))
                //_reposLiveData.value = State.Content(profileRepository.getRepos(UserProfile.PublicUser("ZGoblin")))
            }
            catch (e: UnauthorizedException){
                Log.d(MAIN_DEBUG_TAG, "get content catch UnauthorizedException")
                _userInfoLiveData.value = State.Error(resourceRepository.getString(R.string.error_user_loading))
            }
            catch (e: NotFoundException){
                if (_userInfoLiveData.value == null) {
                    _userInfoLiveData.value = State.Error(USER_NOT_FOUND)
                } else {
                    _reposLiveData.value =
                        State.Error(resourceRepository.getString(R.string.error_not_found))
                }
            }
            catch (e: DataLoadingException){
                if (_userInfoLiveData.value == null) {
                    _userInfoLiveData.value = State.Error(USER_NOT_FOUND)
                } else {
                    _reposLiveData.value =
                        State.Error(resourceRepository.getString(R.string.error_data_loading))
                }
            }
        }
    }
}