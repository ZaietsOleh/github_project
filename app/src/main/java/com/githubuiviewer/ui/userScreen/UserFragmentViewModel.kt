package com.githubuiviewer.ui.userScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.githubuiviewer.data.repository.GitHubRepository
import com.githubuiviewer.datasource.api.GitHubService
import com.githubuiviewer.datasource.api.UnauthorizedException
import com.githubuiviewer.datasource.model.ReposResponse
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.tools.PER_PAGE
import com.githubuiviewer.tools.State
import com.githubuiviewer.tools.UserProfile
import com.githubuiviewer.ui.BaseViewModel
import com.githubuiviewer.ui.userScreen.adapter.PagingDataSource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class UserFragmentViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository,
    private val gitHubService: GitHubService
) : BaseViewModel() {
    lateinit var userProfile: UserProfile

    private val _userInfoLiveData = MutableLiveData<State<UserResponse, Exception>>()
    val userInfoLiveData: LiveData<State<UserResponse, Exception>> = _userInfoLiveData

    private val _reposLiveData = MutableLiveData<PagingData<ReposResponse>>()
    val reposLiveData: LiveData<PagingData<ReposResponse>> = _reposLiveData

    private val _searchLiveData = MutableLiveData<PagingData<UserResponse>>()
    val searchLiveData: LiveData<PagingData<UserResponse>> = _searchLiveData

    val baseScope = baseViewModelScope

    fun getContent() {
        _userInfoLiveData.value = State.Loading
        baseViewModelScope.launch {
            _userInfoLiveData.postValue(State.Content(gitHubRepository.getUser(userProfile)))
            reposFlow().collectLatest { pagedData ->
                _reposLiveData.postValue(pagedData)
            }
        }
    }

    private suspend fun reposFlow(): Flow<PagingData<ReposResponse>> {
        return Pager(PagingConfig(PER_PAGE)) {
            PagingDataSource(baseViewModelScope) { currentPage ->
                gitHubRepository.getRepos(userProfile, currentPage)
            }
        }.flow.cachedIn(baseViewModelScope)
    }

    fun getSearchable(query: String) {
        baseViewModelScope.launch {
            searchFlow(query).collectLatest { pagedData ->
                _searchLiveData.postValue(pagedData)
            }
        }
    }

    private suspend fun searchFlow(query: String): Flow<PagingData<UserResponse>> {
        return Pager(PagingConfig(PER_PAGE)) {
            PagingDataSource(baseViewModelScope) { currentPage ->
                gitHubService.getSearcher(query, PER_PAGE, currentPage).items
            }
        }.flow.cachedIn(baseViewModelScope)
    }

    override fun unauthorizedException() {
        _userInfoLiveData.postValue(State.Error(UnauthorizedException()))
    }
}