package com.elfen.letsred.models

import kotlinx.datetime.Instant

data class User(
    val id: String,
    val name: String,
    val username: String,
    val karma: Int,
    val createdAt: Instant,
    val icon: String,
    val isFollowed: Boolean,
    val banner: String?
)
