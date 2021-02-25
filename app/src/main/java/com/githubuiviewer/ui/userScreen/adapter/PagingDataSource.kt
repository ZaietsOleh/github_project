package com.githubuiviewer.ui.userScreen.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.githubuiviewer.tools.PER_PAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

class PagingDataSource<V : Any>(
    private val scope: CoroutineScope,
    private val dataSource: suspend (currentPage: Int) -> List<V>,
) : PagingSource<Int, V>() {

    override fun getRefreshKey(state: PagingState<Int, V>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        return withContext(scope.coroutineContext) {
            val nextPageNumber = params.key ?: 0
            val response = dataSource(nextPageNumber)
            LoadResult.Page(
                data = response,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (response.size <= PER_PAGE) nextPageNumber + 1 else null
            )
        }
    }
}