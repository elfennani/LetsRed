package com.elfen.letsred.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.elfen.letsred.data.local.models.LocalSession
import com.elfen.letsred.data.local.models.LocalUser
import com.elfen.letsred.data.local.models.asAppModel
import com.elfen.letsred.models.Session
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.seconds

data class SessionAndUser(
    @Embedded
    val session: LocalSession,

    @Relation(parentColumn = "userId", entityColumn = "id")
    val user: LocalUser
)

fun SessionAndUser.asAppModel() = Session(
    user = user.asAppModel(),
    accessToken = session.accessToken,
    refreshToken = session.refreshToken,
    expiration = Instant.fromEpochSeconds(session.expiration),
)