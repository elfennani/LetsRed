package com.elfen.letsred.data.remote.models

import com.elfen.letsred.models.ImageDetails

data class RemoteImage(
    val source: RemoteImageSource,
    val resolutions: List<RemoteImageSource>
)

fun RemoteImage.asAppModel(): ImageDetails {
    return ImageDetails(
        original = source.asAppModel(),
        resolutions = resolutions.map { it.asAppModel() }
    )
}