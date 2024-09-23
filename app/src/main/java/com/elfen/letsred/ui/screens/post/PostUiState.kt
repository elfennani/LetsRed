package com.elfen.letsred.ui.screens.post

import com.elfen.letsred.models.Comment
import com.elfen.letsred.models.Post

data class PostUiState(
    val post: Post? = null,
    val comments: List<Comment> = emptyList()
)
