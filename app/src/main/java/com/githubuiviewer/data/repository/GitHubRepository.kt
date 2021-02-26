package com.githubuiviewer.data.repository

import android.util.Base64
import android.util.Log
import com.githubuiviewer.datasource.api.GitHubService
import com.githubuiviewer.datasource.model.*
import com.githubuiviewer.tools.*
import com.githubuiviewer.tools.sharedPrefsTools.SharedPref
import com.githubuiviewer.ui.issueScreen.IssuesDetailsParameter
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    private val gitHubService: GitHubService,
    private val sharedPref: SharedPref
) {

    /*init {
        sharedPref.token = "bearer 6297b44c3ac15649fb1e7795a______780ee416d693f27"
    }*/

    suspend fun getReadMe(owner: String, repoName: String): String {
        return Base64.decode(gitHubService.getReadme(owner, repoName).content, 0).decodeToString()
    }

    suspend fun updateToken(code: String) {
        Log.d(MAIN_DEBUG_TAG, "LogInRepository start updateToken")
        val response = getAccessToken(code)
        val token = "${response.tokenType} ${response.accessToken}"
        Log.d(MAIN_DEBUG_TAG, "LogInRepository download new token  $token")
        sharedPref.token = token
    }

    private suspend fun getAccessToken(code: String): AccessTokenResponse {
        Log.d(MAIN_DEBUG_TAG, "getAccessToken go to internet")
        return gitHubService.getAccessToken(clientId, clientSecret, code)
    }

    suspend fun getUser(userProfile: UserProfile): UserResponse {
        return when (userProfile) {
            is UserProfile.AuthorizedUser -> gitHubService.getUserByToken()
            is UserProfile.PublicUser -> gitHubService.getUserByNickname(userProfile.userNickname)
        }
    }

    suspend fun getRepos(userProfile: UserProfile, currentPage: Int): List<ReposResponse> {
        return when (userProfile) {
            is UserProfile.AuthorizedUser -> gitHubService.getReposByToken(PER_PAGE, currentPage)
            is UserProfile.PublicUser -> gitHubService.getReposByNickname(
                userProfile.userNickname,
                PER_PAGE,
                currentPage
            )
        }
    }

    suspend fun getIssueComment(
        issuesDetailsParameter: IssuesDetailsParameter,
        page: Int
    ): List<IssueCommentRepos> {
        val result =
            gitHubService.getIssueComments(
                owner = issuesDetailsParameter.owner,
                repo = issuesDetailsParameter.repo,
                issue_number = issuesDetailsParameter.issue_number,
                per_page = PER_PAGE,
                page = page
            )
        if (page == 0) {
            return addAuthorContent(result, issuesDetailsParameter)
        }
        return result
    }

    private suspend fun addAuthorContent(
        comments: List<IssueCommentRepos>,
        issuesDetailsParameter: IssuesDetailsParameter
    ): List<IssueCommentRepos> {
        val author =
            mapToIssueCommentRepos(
                gitHubService.getIssueDetail(
                    owner = issuesDetailsParameter.owner,
                    repo = issuesDetailsParameter.repo,
                    issue_number = issuesDetailsParameter.issue_number,
                )
            )
        val mutable = comments.toMutableList()
        mutable.add(0, author)
        return mutable.toList()
    }

    private fun mapToIssueCommentRepos(issueDetail: IssueDetailRepos): IssueCommentRepos {
        return IssueCommentRepos(
            id = issueDetail.number,
            user = issueDetail.user,
            body = issueDetail.body,
            created_at = issueDetail.created_at,
            reactions = issueDetail.reactions
        )
    }
}