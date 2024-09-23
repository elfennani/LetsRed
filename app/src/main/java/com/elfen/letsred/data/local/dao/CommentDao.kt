package com.elfen.letsred.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.elfen.letsred.data.local.models.LocalComment
import com.elfen.letsred.data.local.models.LocalCommentMore
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Query("DELETE FROM LocalComment WHERE postId=:postId")
    suspend fun clearCommentsByPostId(postId: String)

    @Query("DELETE FROM LocalCommentMore WHERE postId=:postId")
    suspend fun clearMoreByPostId(postId: String)

    @Upsert
    suspend fun upsertComments(comments: List<LocalComment>)

    @Upsert
    suspend fun upsertMoreComments(comments: List<LocalCommentMore>)

    @Query("SELECT * FROM LocalComment WHERE postId=:postId")
    fun getCommentsByPostId(postId: String): Flow<List<LocalComment>>

    @Query("SELECT * FROM LocalCommentMore WHERE postId=:postId")
    fun getMoreByPostId(postId: String): Flow<List<LocalCommentMore>>
}