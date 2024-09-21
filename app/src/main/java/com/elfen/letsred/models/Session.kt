package com.elfen.letsred.models

import kotlinx.datetime.Instant

data class Session(
    val user: User,
    val accessToken: String,
    val refreshToken: String,
    val expiration: Instant
)
