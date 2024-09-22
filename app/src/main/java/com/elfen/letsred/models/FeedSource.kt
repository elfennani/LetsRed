package com.elfen.letsred.models

sealed interface FeedSource {
    data object Best: FeedSource
    data class SavedPosts(val username: String): FeedSource
}

fun FeedSource.asQuery() = when(this){
    is FeedSource.Best -> "best"
    is FeedSource.SavedPosts -> "saved/posts"
}
