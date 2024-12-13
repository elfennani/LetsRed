package com.elfen.letsred.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.elfen.letsred.data.remote.APIService
import com.elfen.letsred.data.remote.models.asAppModel
import com.elfen.letsred.models.FeedSource
import com.elfen.letsred.models.Post

class FeedPagingSource(
    private val query: FeedSource,
    private val apiService: APIService
) : PagingSource<String, Post>() {
    override fun getRefreshKey(state: PagingState<String, Post>): String? {
        return null;
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {
        val nextPageNumber = params.key

        val response = when (query) {
            FeedSource.Best -> apiService.getListingBySort("best", nextPageNumber)
            is FeedSource.SavedPosts -> apiService.getSavedListing(query.username, nextPageNumber)
        }

        return LoadResult.Page(
            data = response.data.children.map { it.data.asAppModel() },
            prevKey = null,
            nextKey = response.data.after
        )
    }
}