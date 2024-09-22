package com.elfen.letsred.models

import kotlinx.datetime.Instant

data class Post(
    val id: String,
    val title: String,
    val community: Community,
    val createdAt: Instant,
    val votes: Int,
    val comments: Int,
    val content: Content?,
    val author: String?,
    val authorId: String?,
    val isDeleted: Boolean,
    val body: String?,
)