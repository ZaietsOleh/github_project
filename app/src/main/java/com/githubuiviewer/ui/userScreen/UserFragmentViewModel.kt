package com.githubuiviewer.ui.userScreen

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.githubuiviewer.R
import com.githubuiviewer.data.repository.ProfileRepository
import com.githubuiviewer.datasource.api.GitHubService
import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.SearchResponse
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.tools.State
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.ui.BaseViewModel
import com.githubuiviewer.ui.userScreen.adapter.PagingDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserFragmentViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val gitHubService: GitHubService
) : BaseViewModel() {
    lateinit var userProfile: UserProfile

    private val _userInfoLiveData = MutableLiveData<State<UserResponse, Int>>()
    val userInfoLiveData: LiveData<State<UserResponse, Int>> = _userInfoLiveData

//    private val _reposLiveData = MutableLiveData<State<List<ReposResponse>, Int>>()
//    val reposLiveData: LiveData<State<List<ReposResponse>, Int>> = _reposLiveData

    private val _reposLiveData = MutableLiveData<PagingData<ReposResponse>>()
    val reposLiveData: LiveData<PagingData<ReposResponse>> = _reposLiveData

    private val _searchLiveData = MutableLiveData<SearchResponse>()
    val searchLiveData: LiveData<SearchResponse> = _searchLiveData

    val baseScope = baseViewModelScope

    private val repos = Pager(PagingConfig(40)) {
        PagingDataSource(baseViewModelScope) { currentPage ->
                profileRepository.getRepos(UserProfile.PublicUser("square"), currentPage)
        }
    }.flow.cachedIn(baseViewModelScope)

    fun getRepos() {
        baseViewModelScope.launch {
            repos.collectLatest { pagedData ->
                _reposLiveData.postValue(pagedData)
            }
        }
    }

    init {
        _userInfoLiveData.value = State.Loading
        //_reposLiveData.value = State.Loading
    }

    private fun setErrorToLiveData(@StringRes textError: Int) {
        if (_userInfoLiveData.value is State.Loading) {
            _userInfoLiveData.postValue(State.Error(R.string.error_not_found))
            // _reposLiveData.postValue(State.Error(R.string.error_not_found))
        } else {
            //_reposLiveData.postValue(State.Error(textError))
        }
    }

    fun getContent() {
        baseViewModelScope.launch {
            launch(Dispatchers.IO) {
                _userInfoLiveData.postValue(State.Content(profileRepository.getUser(userProfile)))
                //_reposLiveData.postValue(State.Content(profileRepository.getRepos(userProfile)))
                //_reposLiveData.postValue(State.Content(profileRepository.getRepos(UserProfile.PublicUser("square"))))
            }
        }
    }

    override fun unauthorizedException() {
        //super.unauthorizedException()
        Log.d("TAGF", "NotFoundExceptionViewModel")
        _userInfoLiveData.postValue(State.Unauthorized)
    }

    fun getSearchable(query: String) {
        viewModelScope.launch(exceptionHandler) {
            launch(Dispatchers.IO) {
                _searchLiveData.value = gitHubService.getSearcher(query)
            }
        }
    }
}