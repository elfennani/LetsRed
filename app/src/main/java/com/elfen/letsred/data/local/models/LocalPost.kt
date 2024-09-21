package com.elfen.letsred.data.local.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elfen.letsred.models.Community
import com.elfen.letsred.models.Content
import com.elfen.letsred.models.Post
import kotlinx.datetime.Instant

@Entity
data class LocalPost(
    @PrimaryKey val id: String,
    val title: String,
    val score: Int,
    val author: String,
    val authorFullId: String,
    val comments: Int,
    val createdAt: Long,
    val link: String,
    val spoiler: Boolean,
    val over18: Boolean,
    val isVideo: Boolean,
    val images: List<LocalImage>?,
    val text: String?,
    @Embedded("community_") val community: LocalPostCommunity,
    @Embedded("video_") val video: LocalVideo?
)

fun LocalPost.asAppModel(): Post {
    var content: Content? = null

    if (!images.isNullOrEmpty()) {
        content = Content.Images(images = images.map { it.asAppModel() })
    } else if (video != null) {
        content = Content.Video(
            url = video.hlsURL,
            width = video.width,
            height = video.height,
            isGIF = video.isGIF
        )
    }else if(!text.isNullOrEmpty()){
        content = Content.Text(text)
    }

    return Post(
        id = id,
        title = title,
        community = Community(id = community.id, name = community.name, icon = community.icon),
        createdAt = Instant.fromEpochMilliseconds(createdAt),
        votes = score,
        comments = comments,
        content = content,
        author = author,
        authorId = authorFullId.replace("t2_", "")
    )
}