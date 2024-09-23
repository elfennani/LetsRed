package com.elfen.letsred.data.local.models

import androidx.room.Entity
import com.elfen.letsred.models.Comment

@Entity(primaryKeys = ["id", "postId"])
data class LocalCommentMore(
    val id: String,
    val postId: String,
    val depth: Int,
    val comments: List<String>,
    val count: Int,
    val order: Float,
)

fun LocalCommentMore.asAppModel() = Comment.More(
    id = id,
    depth = depth,
    order = order
)