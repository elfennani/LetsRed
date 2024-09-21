package com.elfen.letsred.data.local.models

import com.elfen.letsred.data.remote.models.RemoteImageSource
import com.elfen.letsred.models.ImageDetails

data class LocalImage(
    val source: LocalImageSource,
    val resolutions: List<LocalImageSource>
)

fun LocalImage.asAppModel() = ImageDetails(
    original = source.asAppModel(),
    resolutions = resolutions.map { it.asAppModel() }
)
