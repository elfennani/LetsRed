package com.elfen.letsred.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.elfen.letsred.data.local.AppDatabase
import com.elfen.letsred.data.local.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "db")
            .build()

    @Singleton
    @Provides
    fun provideSessionDao(database: AppDatabase) = database.sessionDao()

    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase) = database.userDao()

    @Singleton
    @Provides
    fun providePostDao(database: AppDatabase) = database.postDao()

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context) = context.dataStore
}