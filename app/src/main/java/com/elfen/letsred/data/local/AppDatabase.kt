package com.elfen.letsred.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elfen.letsred.data.local.dao.PostDao
import com.elfen.letsred.data.local.dao.SessionDao
import com.elfen.letsred.data.local.dao.UserDao
import com.elfen.letsred.data.local.models.LocalPagingPost
import com.elfen.letsred.data.local.models.LocalPost
import com.elfen.letsred.data.local.models.LocalSession
import com.elfen.letsred.data.local.models.LocalUser

@Database(
    entities = [LocalSession::class, LocalUser::class, LocalPost::class, LocalPagingPost::class],
    version = 1,
    autoMigrations = [

    ],
    exportSchema = true,
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
}