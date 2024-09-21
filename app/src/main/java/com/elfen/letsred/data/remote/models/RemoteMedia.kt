package com.elfen.letsred.data.remote.models

import com.squareup.moshi.Json

data class RemoteMedia(
    @Json(name = "reddit_video") val video: RemoteVideo
)
