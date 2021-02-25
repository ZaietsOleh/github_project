package com.githubuiviewer.ui.issueScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.githubuiviewer.datasource.api.GitHubService
import com.githubuiviewer.datasource.model.IssueCommentRepos
import com.githubuiviewer.datasource.model.IssueDetailRepos
import com.githubuiviewer.tools.PER_PAGE
import com.githubuiviewer.ui.BaseViewModel
import com.githubuiviewer.ui.userScreen.adapter.PagingDataSource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class IssueViewModel @Inject constructor(
    private val gitHubService: GitHubService
) : BaseViewModel() {

    val baseScope = baseViewModelScope

    private val _commentLiveData = MutableLiveData<PagingData<IssueCommentRepos>>()
    val commentLiveData: LiveData<PagingData<IssueCommentRepos>> = _commentLiveData

    private val comments = Pager(PagingConfig(PER_PAGE)) {
        PagingDataSource(baseViewModelScope) { currentPage ->
            val result =
                gitHubService.getIssueComments("square", "retrofit", 3513, PER_PAGE, currentPage)
            if (currentPage == 0) {
                val author =
                    mapToIssueCommentRepos(gitHubService.getIssueDetail("retrofit", "square", 3513))
                val mutable = result.toMutableList()
                mutable.add(0, author)
                return@PagingDataSource mutable.toList()
            }
            return@PagingDataSource result
        }
    }.flow.cachedIn(baseViewModelScope)

    fun getContent() {
        baseViewModelScope.launch {
            comments.collectLatest { pagedData ->
                _commentLiveData.postValue(pagedData)
            }
        }
    }

    private fun mapToIssueCommentRepos(issueDetail: IssueDetailRepos): IssueCommentRepos {
        return IssueCommentRepos(
            user = issueDetail.user,
            body = issueDetail.body,
            created_at = issueDetail.created_at
        )
    }
}