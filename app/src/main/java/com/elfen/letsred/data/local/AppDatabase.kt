package com.elfen.letsred.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.elfen.letsred.data.local.dao.SessionDao
import com.elfen.letsred.data.local.dao.UserDao
import com.elfen.letsred.data.local.models.LocalSession
import com.elfen.letsred.data.local.models.LocalUser

@Database(
    entities = [LocalSession::class, LocalUser::class],
    version = 1,
    autoMigrations = [],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun userDao(): UserDao
}