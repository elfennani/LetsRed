package com.elfen.letsred.data.local.models

import com.elfen.letsred.models.ImageSource

data class LocalImageSource(
    val url: String,
    val width: Int,
    val height: Int
)

fun LocalImageSource.asAppModel() = ImageSource(
    url = url, width = width, height = height
)