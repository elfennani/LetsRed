package com.elfen.letsred.data.local.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["postId", "query"], unique = true)])
data class LocalPagingPost(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val postId: String,
    val query: String,
    val after: String?,
    val createdAt: Long
)