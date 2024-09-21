package com.elfen.letsred.data.remote.models

import com.squareup.moshi.Json

data class RemoteSubredditDetails(
    @Json(name = "name") val fullId: String,
    @Json(name = "display_name") val displayName: String,
    @Json(name = "community_icon") val communityIcon: String?,
    @Json(name = "icon_img") val iconImage: String?
)