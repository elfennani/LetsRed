package com.elfen.letsred.data.remote.models

import com.elfen.letsred.data.local.models.LocalComment
import com.elfen.letsred.data.local.models.LocalCommentMore
import com.squareup.moshi.Json

data class RemoteComment(
    // Common properties
    val id: String,
    val depth: Int,

    // Normal comment properties
    val body: String?,
    val author: String?,
    val score: Int?,
    @Json(name = "link_id") val postFullId: String?,
    @Json(name = "author_fullname") val authorFullId: String?,
    @Json(name = "created_utc") val createdUTC: Long?,

    // "More" properties
    val count: Int?,
    val children: List<String>?,
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

fun RemoteComment.asMoreCommentsEntity(order: Float, postId: String) = LocalCommentMore(
    id = id,
    postId = postId,
    depth = depth,
    comments = children!!,
    count = count!!,
    order = order
)