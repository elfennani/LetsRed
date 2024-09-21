package com.elfen.letsred.data.remote.models

import com.squareup.moshi.Json

data class RemoteUserCommunity(
    val title: String,
    @Json(name="banner_img") val banner: String?,
)
