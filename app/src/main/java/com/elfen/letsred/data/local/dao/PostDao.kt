package com.elfen.letsred.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.elfen.letsred.data.local.models.LocalPagingPost
import com.elfen.letsred.data.local.models.LocalPost
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Upsert
    suspend fun upsertPosts(posts: List<LocalPost>)

    @Upsert
    suspend fun upsertPost(post: LocalPost)

    @Upsert
    suspend fun upsertPagingPosts(posts: List<LocalPagingPost>)

    @Query("SELECT createdAt FROM LocalPagingPost WHERE `query`=:query ORDER BY createdAt DESC LIMIT 1")
    suspend fun lastUpdatedByQuery(query: String): Long?

    @Query("SELECT * FROM LocalPost WHERE id=:id")
    fun getPostById(id: String): Flow<LocalPost?>

    @Query("SELECT * FROM LocalPost WHERE id IN (:idList)")
    suspend fun getPostsById(idList: List<String>): List<LocalPost>

    @Query("SELECT * FROM LocalPost")
    fun getPosts(): Flow<List<LocalPost>>

    @Query("SELECT * FROM LocalPagingPost WHERE `query`=:query")
    fun getPostsByQuery(query: String): PagingSource<Int, LocalPagingPost>

    @Query("SELECT * FROM LocalPagingPost WHERE `query`=:query")
    suspend fun getLastPagingByQuery(query: String): LocalPagingPost

    @Query("SELECT * FROM LocalPagingPost WHERE postId=:postId")
    suspend fun getPagingDataByPostId(postId: String): LocalPagingPost

    @Query("DELETE FROM LocalPagingPost WHERE `query`=:query")
    fun deleteByQuery(query:String)
}