package com.elfen.letsred.data.repository

import com.elfen.letsred.data.local.AppDatabase
import com.elfen.letsred.data.remote.APIService
import com.elfen.letsred.data.remote.models.RemoteDataType
import com.elfen.letsred.data.remote.models.asAppModel
import com.elfen.letsred.models.Comment
import com.elfen.letsred.models.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class PostRepository(
    private val apiService: APIService,
    private val database: AppDatabase
) {
    suspend fun fetchPostDetailsById(postId: String): Pair<Post, List<Comment>>{
        return withContext(Dispatchers.IO){
            val response = apiService.getPostComments(postId)
            val post = (response.first().data.children.first() as RemoteDataType.Post).data.asAppModel()
            val comments = response[1].data.asAppModel()

            return@withContext Pair(post, comments)
        }
    }

    fun postDetailsById(postId: String) = flow<Pair<Post, List<Comment>>> {
        emit(fetchPostDetailsById(postId))
    }

}