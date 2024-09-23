package com.elfen.letsred.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.elfen.letsred.data.local.AppDatabase
import com.elfen.letsred.data.local.dao.CommentDao
import com.elfen.letsred.data.local.dao.PostDao
import com.elfen.letsred.data.local.dao.SessionDao
import com.elfen.letsred.data.local.dao.UserDao
import com.elfen.letsred.data.remote.APIService
import com.elfen.letsred.data.remote.AuthAPIService
import com.elfen.letsred.data.repository.FeedRepository
import com.elfen.letsred.data.repository.PostRepository
import com.elfen.letsred.data.repository.SessionRepository
import com.elfen.letsred.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideSessionRepository(
        @ApplicationContext context: Context,
        authAPIService: AuthAPIService,
        sessionDao: SessionDao,
        @GeneralDatabase appDatabase: AppDatabase,
        dataStore: DataStore<Preferences>
    ) = SessionRepository(context, authAPIService, sessionDao, appDatabase.userDao(), dataStore)

    @Singleton
    @Provides
    fun provideUserRepository(
        apiService: APIService,
        userDao: UserDao
    ) = UserRepository(apiService, userDao)

    @Singleton
    @Provides
    fun provideFeedRepository(
        apiService: APIService,
        postDao: PostDao,
        @UserDatabase appDatabase: AppDatabase
    ) = FeedRepository(apiService, postDao, appDatabase)

    @Singleton
    @Provides
    fun providePostRepository(
        apiService: APIService,
        postDao: PostDao,
        commentDao: CommentDao,
        @UserDatabase appDatabase: AppDatabase
    ) = PostRepository(apiService, postDao, commentDao, appDatabase)
}