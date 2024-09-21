package com.elfen.letsred.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elfen.letsred.models.User
import kotlinx.datetime.Instant

@Entity
data class LocalUser(
    @PrimaryKey val id: String,
    val name: String?,
    val username: String,
    val karma: Int,
    val isFollowed: Boolean,
    val banner: String?,
    val icon: String?,
    val createdAt: Long,
    val over18: Boolean
)

fun LocalUser.asAppModel() = User(
    id = id,
    name = name,
    username = username,
    karma = karma,
    createdAt = Instant.fromEpochMilliseconds(createdAt),
    icon = icon,
    isFollowed = isFollowed,
    banner = banner,
    over18 = over18
)