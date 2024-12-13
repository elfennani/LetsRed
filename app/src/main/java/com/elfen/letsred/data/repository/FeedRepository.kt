package com.elfen.letsred.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.elfen.letsred.data.paging.FeedPagingSource
import com.elfen.letsred.data.remote.APIService
import com.elfen.letsred.models.FeedSource
import com.elfen.letsred.models.Post
import kotlinx.coroutines.flow.Flow

class FeedRepository(private val apiService: APIService) {
    @OptIn(ExperimentalPagingApi::class)
    fun feedPagerByQuery(query: FeedSource): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
        ) { FeedPagingSource(query, apiService) }
            .flow
    }
}