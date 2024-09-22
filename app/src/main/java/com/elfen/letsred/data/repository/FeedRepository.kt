package com.elfen.letsred.data.repository

import androidx.compose.ui.util.fastAny
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.elfen.letsred.data.local.AppDatabase
import com.elfen.letsred.data.local.dao.PostDao
import com.elfen.letsred.data.local.models.asAppModel
import com.elfen.letsred.data.paging.FeedRemoteMediator
import com.elfen.letsred.data.remote.APIService
import com.elfen.letsred.models.FeedSource
import com.elfen.letsred.models.Post
import com.elfen.letsred.models.asQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class FeedRepository(
    private val apiService: APIService,
    private val postDao: PostDao,
    private val appDatabase: AppDatabase,
) {
    @OptIn(ExperimentalPagingApi::class)
    fun feedPagerByQuery(query: FeedSource): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            remoteMediator = FeedRemoteMediator(query, appDatabase, postDao, apiService)
        ) { postDao.getPostsByQuery(query.asQuery()) }
            .flow
            .map {
                it.map { pagingPost -> postDao.getPostById(pagingPost.postId).first()!! }
            }
            .map { pagingData ->
                pagingData.map { pagingPost -> pagingPost.asAppModel() }
            }
    }
}