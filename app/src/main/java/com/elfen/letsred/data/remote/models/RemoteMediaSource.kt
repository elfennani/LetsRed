package com.elfen.letsred.data.remote.models

import com.squareup.moshi.Json

data class RemoteMediaSource(
    @Json(name = "x") val width: Int,
    @Json(name = "y") val height: Int,
    @Json(name = "u") val url: String
)
