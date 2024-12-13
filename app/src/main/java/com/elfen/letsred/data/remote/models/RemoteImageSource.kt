package com.elfen.letsred.data.remote.models

import com.elfen.letsred.models.ImageSource

data class RemoteImageSource(
    val url: String,
    val width: Int,
    val height: Int
)

fun RemoteImageSource.asAppModel() = ImageSource(
    url = url,
    width = width,
    height = height
)