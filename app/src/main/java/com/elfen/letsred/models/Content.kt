package com.elfen.letsred.models

sealed interface Content {
    data class Images(val images: List<ImageDetails>) : Content

    data class Video(
        val url: String,
        val width: Int,
        val height: Int,
        val isGIF: Boolean
    ) : Content

    data class CrossPost(val post: Post) : Content
}
