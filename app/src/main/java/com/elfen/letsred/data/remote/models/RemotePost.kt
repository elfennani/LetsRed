package com.elfen.letsred.data.remote.models

import com.elfen.letsred.data.local.models.asAppModel
import com.elfen.letsred.models.Community
import com.elfen.letsred.models.Content
import com.elfen.letsred.models.Post
import com.elfen.letsred.utilities.decodeEntities
import com.elfen.letsred.utilities.emptyAsNull
import com.squareup.moshi.Json
import kotlinx.datetime.Instant

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

fun RemotePost.asAppModel(): Post{
    var content: Content? = null

    var images = preview?.images?.map { image ->
        RemoteImage(
            source = RemoteImageSource(
                url = image.source.url.decodeEntities(),
                width = image.source.width,
                height = image.source.height
            ),
            resolutions = image.resolutions.map { res ->
                RemoteImageSource(
                    url = res.url.decodeEntities(),
                    width = res.width,
                    height = res.height
                )
            }
        )
    }

    var video = media?.video

    if (mediaMetadata != null) {
        if (mediaMetadata.none { it.value.isVideo() }) {
            images = mediaMetadata.map { (id, image) ->
                RemoteImage(
                    source = RemoteImageSource(
                        url = image.source!!.url.decodeEntities(),
                        width = image.source.width,
                        height = image.source.height
                    ),
                    resolutions = image.previews!!.map { res ->
                        RemoteImageSource(
                            url = res.url.decodeEntities(),
                            width = res.width,
                            height = res.height
                        )
                    }
                )
            }
        } else {
            video = mediaMetadata.entries.first().let { (key, value) ->
                RemoteVideo(
                    hlsURL = value.hlsUrl!!.decodeEntities(),
                    duration = -1,
                    width = value.width!!,
                    height = value.height!!,
                    fallbackURL = null,
                    isGIF = value.isGif == true
                )
            }
        }
    }



    if (!images.isNullOrEmpty()) {
        content = Content.Images(images = images.map { it.asAppModel() })
    } else if (video != null) {
        content = Content.Video(
            url = video.hlsURL,
            width = video.width,
            height = video.height,
            isGIF = video.isGIF
        )
    }

    return Post(
        id = id,
        title = title.decodeEntities(),
        community = Community(
            id = subredditDetails.fullId.replace("t5_", ""),
            name = subredditDetails.displayName,
            icon = subredditDetails.iconImage?.emptyAsNull()?.decodeEntities()
                ?: subredditDetails.communityIcon?.emptyAsNull()?.decodeEntities()
        ),
        createdAt = Instant.fromEpochMilliseconds(createdAt * 1000),
        votes = score,
        comments = numComments,
        content = content,
        author = author.takeIf { it != "[deleted]" },
        authorId = authorFullId,
        isDeleted = author == "[deleted]",
        body = selfTextHtml?.emptyAsNull()?.decodeEntities(),
    )
}