package com.elfen.letsred.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.elfen.letsred.data.local.AppDatabase
import com.elfen.letsred.data.local.dataStore
import com.elfen.letsred.data.repository.SessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeneralDatabase

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserDatabase

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    @GeneralDatabase
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "db")
            .build()

    @Singleton
    @Provides
    @UserDatabase
    fun provideUserDatabase(
        @ApplicationContext context: Context,
        dataStore: DataStore<Preferences>
    ): AppDatabase {
        val currentUser = runBlocking { dataStore.data.first()[SessionRepository.SESSION_USER_KEY] }
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "db_user_$currentUser")
            .build()
    }


    @Singleton
    @Provides
    fun provideSessionDao(@GeneralDatabase database: AppDatabase) = database.sessionDao()

    @Singleton
    @Provides
    fun provideUserDao(@UserDatabase database: AppDatabase) = database.userDao()

    @Singleton
    @Provides
    fun providePostDao(@UserDatabase database: AppDatabase) = database.postDao()

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context) = context.dataStore
}