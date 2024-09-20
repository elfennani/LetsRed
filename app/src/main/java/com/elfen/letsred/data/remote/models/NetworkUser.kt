package com.elfen.letsred.data.remote.models

import com.squareup.moshi.Json

data class NetworkUser(
    val id: String,
    val name: String,
    val subreddit: NetworkUserCommunity,
    @Json(name = "icon_img") val icon: String,
    @Json(name = "total_karma") val karma: Int
)
