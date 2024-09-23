package com.elfen.letsred.data.remote.models

import com.elfen.letsred.data.local.models.LocalComment
import com.elfen.letsred.data.local.models.LocalCommentMore
import com.squareup.moshi.Json
import java.nio.file.Files.find

data class RemoteComment(
    // Common properties
    val id: String,
    val depth: Int,

    // Normal comment properties
    val body: String,
    val author: String?,
    val score: Int,
    @Json(name = "link_id") val postFullId: String,
    @Json(name = "author_fullname") val authorFullId: String?,
    @Json(name = "created_utc") val createdUTC: Long,
)

data class RemoteMoreComment(
    val id: String,
    val depth: Int,
    val count: Int,
    val children: List<String>,
)

fun RemoteComment.asCommentEntity(order: Float, moreId: String? = null) = LocalComment(
    id = id,
    postId = postFullId!!.substring(3),
    body = body!!,
    author = if (author.isNullOrEmpty() || authorFullId.isNullOrEmpty()) null else author,
    authorId = if (author.isNullOrEmpty() || authorFullId.isNullOrEmpty()) null
    else authorFullId.substring(3),
    depth = depth,
    moreId = moreId,
    votes = score!!,
    createdAt = createdUTC!! * 1000,
    order = order
)

fun RemoteMoreComment.asMoreCommentsEntity(order: Float, postId: String) = LocalCommentMore(
    id = id,
    postId = postId,
    depth = depth,
    comments = children!!,
    count = count!!,
    order = order
)

fun RemotePage<RemoteDataType>.asCommentsEntityPair(): Pair<List<LocalComment>, List<LocalCommentMore>> {
    val comments: MutableList<LocalComment> = mutableListOf()
    var moreComments: MutableList<LocalCommentMore> = mutableListOf()

    val postId = children
        .filterIsInstance<RemoteDataType.Comment>()
        .first { !it.data.postFullId.isNullOrEmpty() }
        ?.data?.postFullId?.substring(3) ?: return Pair(emptyList(), emptyList())

    children.forEachIndexed { index, comment ->
        if (comment is RemoteDataType.MoreComment) {
            moreComments += comment.data.asMoreCommentsEntity(
                order = index.toFloat(),
                postId = postId
            )
        } else if (comment is RemoteDataType.Comment) {
            comments += comment.data.asCommentEntity(index.toFloat())
        }
    }

    return Pair(comments, moreComments)
}