package com.elfen.letsred.models

data class ImageDetails(
    val original: ImageSource,
    val resolutions: List<ImageSource>
)
