package com.elfen.letsred.data.remote.models

import com.elfen.letsred.data.local.models.LocalUser
import com.elfen.letsred.utilities.decodeEntities
import com.elfen.letsred.utilities.emptyAsNull
import com.squareup.moshi.Json

data class RemoteUser(
    val id: String,
    val name: String,
    val subreddit: RemoteUserCommunity,
    @Json(name = "icon_img") val icon: String?,
    @Json(name = "total_karma") val karma: Int,
    @Json(name="has_subscribed") val subscribed: Boolean,
    @Json(name = "created_utc") val createdUTC: Long,
    @Json(name = "over_18") val over18: Boolean,
)

fun RemoteUser.asEntity() = LocalUser(
    id = id,
    name = subreddit.title.emptyAsNull()?.decodeEntities(),
    username = name,
    karma = karma,
    isFollowed = subscribed,
    banner = subreddit.banner?.emptyAsNull()?.decodeEntities(),
    icon = icon?.emptyAsNull()?.decodeEntities(),
    createdAt = createdUTC*1000,
    over18 = over18
)