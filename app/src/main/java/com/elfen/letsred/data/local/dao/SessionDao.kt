package com.elfen.letsred.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.elfen.letsred.data.local.models.LocalSession
import com.elfen.letsred.data.local.relations.SessionAndUser
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Transaction
    @Query("SELECT * FROM LocalSession")
    fun getSessionsWithUsersFlow(): Flow<List<SessionAndUser>>

    @Query("SELECT * FROM LocalSession WHERE userId=:userId")
    suspend fun getSessionAndUserById(userId: String): SessionAndUser?

    @Upsert
    suspend fun upsertSession(session: LocalSession)
}