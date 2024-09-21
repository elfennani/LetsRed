package com.elfen.letsred.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elfen.letsred.models.Session

@Entity
data class LocalSession(
    @PrimaryKey val userId: String,
    val accessToken: String,
    val refreshToken: String,
    val expiration: Long
)
