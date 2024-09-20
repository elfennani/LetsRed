package com.elfen.letsred.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elfen.letsred.data.local.dao.SessionDao
import com.elfen.letsred.data.local.models.LocalSession

@Database(
    entities = [LocalSession::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun sessionDao(): SessionDao
}