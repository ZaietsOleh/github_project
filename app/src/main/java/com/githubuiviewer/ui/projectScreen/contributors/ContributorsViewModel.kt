package com.githubuiviewer.ui.projectScreen.contributors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.githubuiviewer.datasource.api.GitHubService
import com.githubuiviewer.datasource.model.UserResponse
import com.githubuiviewer.tools.PER_PAGE
import com.githubuiviewer.ui.BaseViewModel
import com.githubuiviewer.ui.projectScreen.UserAndRepoName
import com.githubuiviewer.tools.PagingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.githubuiviewer.tools.State

class ContributorsViewModel @Inject constructor(
    private val gitHubService: GitHubService
) : BaseViewModel() {

    private val _contributorsLiveData =
        MutableLiveData<State<PagingData<UserResponse>, Exception>>()
    val contributorsLiveData
        get() = _contributorsLiveData as LiveData<State<PagingData<UserResponse>, Exception>>

    val baseView = baseViewModelScope

    fun getContributors(userAndRepoName: UserAndRepoName) {
        baseViewModelScope.launch {
            _contributorsLiveData.postValue(State.Loading)
            reposFlow(
                userAndRepoName.userName,
                userAndRepoName.repoName
            ).collectLatest { pagedData ->
                _contributorsLiveData.postValue(State.Content(pagedData))
            }
        }
    }

    private suspend fun reposFlow(owner: String, repoName: String): Flow<PagingData<UserResponse>> {
        return Pager(PagingConfig(PER_PAGE)) {
            PagingDataSource(baseViewModelScope) { currentPage ->
                gitHubService.getContributors(owner, repoName, PER_PAGE, currentPage)
            }
        }.flow.cachedIn(baseViewModelScope)
    }

    override fun unauthorizedException() {
        super.unauthorizedException()
        //_contributorsLiveData.value = State.Error()
    }

    override fun dataLoadingException() {
        super.dataLoadingException()
    }
}