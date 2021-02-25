package com.githubuiviewer.ui.issueScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.githubuiviewer.datasource.api.GitHubService
import com.githubuiviewer.datasource.model.IssueCommentRepos
import com.githubuiviewer.datasource.model.IssueDetailRepos
import com.githubuiviewer.datasource.model.ReactionContent
import com.githubuiviewer.tools.Emoji
import com.githubuiviewer.tools.PER_PAGE
import com.githubuiviewer.ui.BaseViewModel
import com.githubuiviewer.ui.userScreen.adapter.PagingDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class IssueViewModel @Inject constructor(
    private val gitHubService: GitHubService
) : BaseViewModel() {

    val baseScope = baseViewModelScope

    private val _commentLiveData = MutableLiveData<PagingData<IssueCommentRepos>>()
    val commentLiveData: LiveData<PagingData<IssueCommentRepos>> = _commentLiveData

    fun getContent() {
        baseViewModelScope.launch {
            commentsFlow().collectLatest { pagedData ->
                _commentLiveData.postValue(pagedData)
            }
        }
    }

    fun createReaction(reaction: Emoji, issueCommentRepos: IssueCommentRepos) {
        baseViewModelScope.launch(Dispatchers.IO) {
            gitHubService.createReactionForIssueComment("square", "retrofit", issueCommentRepos.id, content = ReactionContent(reaction.githubReaction))
            getContent()
        }
    }

    private suspend fun commentsFlow(): Flow<PagingData<IssueCommentRepos>> {
        return Pager(PagingConfig(PER_PAGE)) {
            PagingDataSource(baseViewModelScope) { currentPage ->
                val result =
                    gitHubService.getIssueComments(
                        "square",
                        "retrofit",
                        3513,
                        PER_PAGE,
                        currentPage
                    )
                if (currentPage == 0) {
                    return@PagingDataSource addAuthorContent(result)
                }
                return@PagingDataSource result
            }
        }.flow.cachedIn(baseViewModelScope)
    }

    private suspend fun addAuthorContent(comments: List<IssueCommentRepos>): List<IssueCommentRepos> {
        val author =
            mapToIssueCommentRepos(gitHubService.getIssueDetail("retrofit", "square", 3513))
        val mutable = comments.toMutableList()
        mutable.add(0, author)
        return mutable.toList()
    }

    private fun mapToIssueCommentRepos(issueDetail: IssueDetailRepos): IssueCommentRepos {
        return IssueCommentRepos(
            id = 0,
            user = issueDetail.user,
            body = issueDetail.body,
            created_at = issueDetail.created_at,
            reactions = issueDetail.reactions
        )
    }
}