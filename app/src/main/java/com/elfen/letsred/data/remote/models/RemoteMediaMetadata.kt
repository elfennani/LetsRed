package com.elfen.letsred.data.remote.models

import com.squareup.moshi.Json

data class RemoteMediaMetadata(
    @Json(name = "e") val type: String,
    @Json(name = "m") val mimeType: String?,
    @Json(name = "p") val previews: List<RemoteMediaSource>?,
    @Json(name = "s") val source: RemoteMediaSource?,
    val hlsUrl: String?,
    val isGif: Boolean?,
    @Json(name = "x") val width: Int?,
    @Json(name = "y") val height: Int?,
){
    companion object{
        const val REMOTE_MEDIA_METADATA_VIDEO = "RedditVideo"
        const val REMOTE_MEDIA_METADATA_IMAGE = "Image"
    }

    fun isImage() = type == REMOTE_MEDIA_METADATA_IMAGE
    fun isVideo() = type == REMOTE_MEDIA_METADATA_VIDEO
}
