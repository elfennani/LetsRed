package com.elfen.letsred.data.local.models

import androidx.room.Entity
import com.elfen.letsred.models.Comment

@Entity(primaryKeys = ["id", "postId"])
data class LocalComment(
    val id: String,
    val postId: String,
    val body: String,
    val author: String?,
    val authorId: String?,
    val depth: Int,
    val moreId: String?,
    val votes: Int,
    val createdAt: Long,
    val order: Float,
)

fun LocalComment.asAppModel() = Comment.Content(
    id = id,
    body = body,
    authorId = authorId,
    authorUsername = author,
    depth = depth,
    order = order
)