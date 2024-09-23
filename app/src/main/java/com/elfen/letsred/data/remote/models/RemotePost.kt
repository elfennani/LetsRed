package com.elfen.letsred.data.remote.models

import com.elfen.letsred.data.local.models.LocalImage
import com.elfen.letsred.data.local.models.LocalImageSource
import com.elfen.letsred.data.local.models.LocalPost
import com.elfen.letsred.data.local.models.LocalPostCommunity
import com.elfen.letsred.data.local.models.LocalVideo
import com.elfen.letsred.utilities.decodeEntities
import com.elfen.letsred.utilities.emptyAsNull
import com.squareup.moshi.Json

data class RemotePost(
    val id: String,
    val title: String,
    val score: Int,
    val author: String,
    @Json(name = "author_fullname") val authorFullId: String?,
    @Json(name = "num_comments") val numComments: Int,
    @Json(name = "created_utc") val createdAt: Long,
    val preview: RemotePreview?,
    val media: RemoteMedia?,
    val permalink: String,
    val spoiler: Boolean,
    @Json(name = "over_18") val over18: Boolean,
    @Json(name = "is_video") val isVideo: Boolean,
    @Json(name = "selftext_html") val selfTextHtml: String?,
    @Json(name = "sr_detail") val subredditDetails: RemoteSubredditDetails,
    @Json(name = "media_metadata") val mediaMetadata: Map<String, RemoteMediaMetadata>?
)

fun RemotePost.asEntity(): LocalPost {
    var images = preview?.images?.map { image ->
        LocalImage(
            source = LocalImageSource(
                url = image.source.url.decodeEntities(),
                width = image.source.width,
                height = image.source.height
            ),
            resolutions = image.resolutions.map { res ->
                LocalImageSource(
                    url = res.url.decodeEntities(),
                    width = res.width,
                    height = res.height
                )
            }
        )
    }

    var video = media?.video?.let {
        LocalVideo(
            hlsURL = it.hlsURL.decodeEntities(),
            duration = it.duration,
            width = it.width,
            height = it.height,
            fallbackURL = it.fallbackURL.decodeEntities(),
            isGIF = it.isGIF
        )
    }

    if (mediaMetadata != null) {
        if (mediaMetadata.none { it.value.isVideo() }) {
            images = mediaMetadata.map { (id, image) ->
                LocalImage(
                    source = LocalImageSource(
                        url = image.source!!.url.decodeEntities(),
                        width = image.source.width,
                        height = image.source.height
                    ),
                    resolutions = image.previews!!.map { res ->
                        LocalImageSource(
                            url = res.url.decodeEntities(),
                            width = res.width,
                            height = res.height
                        )
                    }
                )
            }
        } else {
            video = mediaMetadata.entries.first().let { (key, value) ->
                LocalVideo(
                    hlsURL = value.hlsUrl!!.decodeEntities(),
                    duration = -1,
                    width = value.width!!,
                    height = value.height!!,
                    fallbackURL = null,
                    isGIF = value.isGif ?: false
                )
            }
        }
    }

    return LocalPost(
        id = id,
        title = title.decodeEntities(),
        score = score,
        author = author.takeIf { it != "[deleted]" },
        authorFullId = authorFullId,
        comments = numComments,
        createdAt = createdAt * 1000,
        link = permalink.decodeEntities(),
        spoiler = spoiler,
        over18 = over18,
        isVideo = isVideo,
        text = selfTextHtml?.emptyAsNull()?.decodeEntities(),
        community = LocalPostCommunity(
            id = subredditDetails.fullId.replace("t5_", ""),
            name = subredditDetails.displayName,
            icon = subredditDetails.iconImage?.emptyAsNull()?.decodeEntities()
                ?: subredditDetails.communityIcon?.emptyAsNull()?.decodeEntities()
        ),
        images = images,
        video = video
    )
}

fun RemoteDataType.asPostEntity(): LocalPost {
    if(this is RemoteDataType.Post){
        return data.asEntity()
    }

    throw Exception("RemoteDataType is not RemotePost")
}