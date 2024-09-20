package com.elfen.letsred.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalSession(
    @PrimaryKey val userId: String,
    val accessToken: String,
    val username: String,
    val userFullName: String,
    val refreshToken: String,
    val expiration: Long
)