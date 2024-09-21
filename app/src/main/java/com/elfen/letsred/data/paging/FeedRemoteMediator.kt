package com.elfen.letsred.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.elfen.letsred.data.local.AppDatabase
import com.elfen.letsred.data.local.dao.PostDao
import com.elfen.letsred.data.local.models.LocalPagingPost
import com.elfen.letsred.data.local.models.LocalPost
import com.elfen.letsred.data.remote.APIService
import com.elfen.letsred.data.remote.models.asEntity
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.hours

@OptIn(ExperimentalPagingApi::class)
class FeedRemoteMediator(
    private val query: String,
    private val appDatabase: AppDatabase,
    private val postDao: PostDao,
    private val apiService: APIService
) : RemoteMediator<Int, LocalPagingPost>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalPagingPost>
    ): MediatorResult {
        Log.d("FeedRemoteMediator", "load: $loadType")
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )

                    lastItem.after
                }
            }

            val response = apiService.getListingBySort(query, loadKey)
            val now = Clock.System.now().toEpochMilliseconds()

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    postDao.deleteByQuery(query)
                }

                postDao.upsertPosts(response.data.children.map { post -> post.data.asEntity() })
                postDao.upsertPagingPosts(response.data.children.map { post ->
                    LocalPagingPost(
                        postId = post.data.id,
                        query = query,
                        after = response.data.after,
                        createdAt = now
                    )
                })
            }

            MediatorResult.Success(endOfPaginationReached = response.data.after == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val hour = 1.hours.inWholeMilliseconds
        val lastUpdated = postDao.lastUpdatedByQuery(query) ?: 0

        return if (Clock.System.now().toEpochMilliseconds() - lastUpdated < hour) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }
}