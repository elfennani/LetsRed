package com.elfen.letsred.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.elfen.letsred.R
import com.elfen.letsred.data.local.dao.SessionDao
import com.elfen.letsred.data.local.models.LocalSession
import com.elfen.letsred.data.remote.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class SessionRepository(
    private val context: Context,
    private val apiService: APIService,
    private val sessionDao: SessionDao,
    private val dataStore: DataStore<Preferences>
) {
    private val scopes = listOf(
        "identity",
        "edit",
        "flair",
        "history",
        "modconfig",
        "modflair",
        "modlog",
        "modposts",
        "modwiki",
        "mysubreddits",
        "privatemessages",
        "read",
        "report",
        "save",
        "submit",
        "subscribe",
        "vote",
        "wikiedit",
        "wikiread"
    )
    private val clientId = context.getString(R.string.client_id)
    private val redirectUri = context.getString(R.string.redirect_uri)

    fun initiateAuthentication() {
        val url = Uri.Builder()
            .scheme("https")
            .authority("www.reddit.com")
            .path("/api/v1/authorize")
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("state", "random_state")
            .appendQueryParameter("redirect_uri", redirectUri)
            .appendQueryParameter("duration", "permanent")
            .appendQueryParameter("scope", scopes.joinToString(","))
            .build()

        val intent = Intent(Intent.ACTION_VIEW, url)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    suspend fun validateToken(code: String) {
        withContext(Dispatchers.IO) {
            val auth = Base64
                .encodeToString("$clientId:".toByteArray(), Base64.NO_WRAP)
            Log.d("SessionRepository", "validateToken: $auth")

            val token = apiService.getAccessToken(
                authorization = "Basic $auth",
                code = code,
                redirectUri = redirectUri,
            )
            val user = apiService.getCurrentUser(authorization = "Bearer ${token.accessToken}")

            sessionDao.upsertSession(
                session = LocalSession(
                    accessToken = token.accessToken,
                    userId = user.id,
                    username = user.name,
                    userFullName = user.subreddit.title,
                    refreshToken = token.refreshToken,
                    expiration = Clock.System.now().epochSeconds + token.expiresIn
                )
            )

            dataStore.edit {
                it[SESSION_USER_KEY] = user.id
            }
        }
    }

    companion object {
        val SESSION_USER_KEY = stringPreferencesKey("session_user_id")
    }
}