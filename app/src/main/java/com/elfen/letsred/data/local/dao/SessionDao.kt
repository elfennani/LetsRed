package com.elfen.letsred.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.elfen.letsred.data.local.models.LocalSession

@Dao
interface SessionDao {
    @Query("SELECT * FROM LocalSession WHERE userId=:userId")
    suspend fun getSessionById(userId: String): LocalSession

    @Upsert
    suspend fun upsertSession(session: LocalSession)
}