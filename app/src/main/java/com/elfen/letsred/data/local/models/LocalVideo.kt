package com.elfen.letsred.data.local.models

data class LocalVideo(
    val hlsURL: String,
    val duration: Int,
    val width: Int,
    val height: Int,
    val fallbackURL: String?,
    val isGIF: Boolean
)

