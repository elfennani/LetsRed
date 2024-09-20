package com.elfen.letsred.data.remote.models

import com.elfen.letsred.data.local.models.LocalSession
import com.squareup.moshi.Json

data class NetworkAccessToken(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "token_type") val tokenType: String,
    @Json(name = "expires_in") val expiresIn: Int,
    @Json(name = "refresh_token") val refreshToken: String,
    val scope: String
)