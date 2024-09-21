package com.elfen.letsred.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.elfen.letsred.data.local.models.LocalUser

@Dao
interface UserDao {
    @Query("SELECT * FROM LocalUser WHERE id=:id")
    suspend fun getUserById(id:String): LocalUser?

    @Upsert
    suspend fun upsertUser(user: LocalUser)
}