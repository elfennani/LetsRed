package com.elfen.letsred.data.repository

import android.util.Log
import androidx.room.withTransaction
import com.elfen.letsred.data.local.AppDatabase
import com.elfen.letsred.data.local.dao.CommentDao
import com.elfen.letsred.data.local.dao.PostDao
import com.elfen.letsred.data.local.models.asAppModel
import com.elfen.letsred.data.remote.APIService
import com.elfen.letsred.data.remote.models.asCommentEntities
import com.elfen.letsred.data.remote.models.asEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.withContext

class PostRepository(
    private val apiService: APIService,
    private val postDao: PostDao,
    private val commentDao: CommentDao,
    private val database: AppDatabase
) {
    suspend fun fetchPostComments(postId: String) {
        withContext(Dispatchers.IO) {
            val postComments = apiService.getPostComments(postId)
            val (comment, moreComments) = postComments.comments.data.asCommentEntities()
            val post = postComments.post.data.children.first().data.asEntity()

            database.withTransaction {
                commentDao.clearCommentsByPostId(postId)
                commentDao.clearMoreByPostId(postId)

                postDao.upsertPost(post)
                commentDao.upsertComments(comment)
                commentDao.upsertMoreComments(moreComments)
            }
        }
    }

    fun commentsById(postId: String) = commentDao
        .getCommentsByPostId(postId)
        .zip(commentDao.getMoreByPostId(postId)) { comments, moreComments ->
            val appComments = comments.map { it.asAppModel() }
            val appMoreComments = moreComments.map { it.asAppModel() }

            (appComments + appMoreComments).sortedBy { it.order }
        }

    fun postById(postId: String) = postDao.getPostById(postId).map { it?.asAppModel() }
}