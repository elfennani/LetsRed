package com.elfen.letsred.data.remote.models

import com.squareup.moshi.Json

data class RemoteVideo(
    @Json(name = "hls_url") val hlsURL: String,
    val duration: Int,
    val width: Int,
    val height: Int,
    @Json(name = "fallback_url") val fallbackURL: String,
    @Json(name = "is_gif") val isGIF: Boolean
)
