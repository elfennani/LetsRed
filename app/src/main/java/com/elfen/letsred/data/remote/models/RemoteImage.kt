package com.elfen.letsred.data.remote.models

data class RemoteImage(
    val source: RemoteImageSource,
    val resolutions: List<RemoteImageSource>
)
