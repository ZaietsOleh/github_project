package com.githubuiviewer.ui.userScreen.adapter

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.githubuiviewer.datasource.api.GitHubService
import com.githubuiviewer.datasource.model.ReposResponse
import java.lang.Exception

class ReposDataSource(
    private val gitHubService: GitHubService
) : PagingSource<Int, ReposResponse>() {

    override fun getRefreshKey(state: PagingState<Int, ReposResponse>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReposResponse> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = gitHubService.getReposByNickname("square", 40, nextPageNumber)
            LoadResult.Page(
                data = response,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (response.size <= 40) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}