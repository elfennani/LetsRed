package com.elfen.letsred.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.elfen.letsred.data.local.models.LocalUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM LocalUser WHERE id=:id")
    fun getUserById(id:String): Flow<LocalUser?>

    @Query("SELECT * FROM LocalUser WHERE username=:username")
    fun getUserByUsername(username:String): Flow<LocalUser?>

    @Upsert
    suspend fun upsertUser(user: LocalUser)
}