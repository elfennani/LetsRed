package com.elfen.letsred.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.elfen.letsred.R
import com.elfen.letsred.data.local.dao.SessionDao
import com.elfen.letsred.data.local.dao.UserDao
import com.elfen.letsred.data.local.models.LocalSession
import com.elfen.letsred.data.local.models.asAppModel
import com.elfen.letsred.data.local.relations.asAppModel
import com.elfen.letsred.data.remote.APIService
import com.elfen.letsred.data.remote.models.asEntity
import com.elfen.letsred.utilities.decodeEntities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class SessionRepository(
    private val context: Context,
    private val apiService: APIService,
    private val sessionDao: SessionDao,
    private val userDao: UserDao,
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

    val sessions = sessionDao.getSessionsWithUsersFlow()
        .map { list -> list.map { it.asAppModel() } }

    val activeSession = dataStore.data.map { it[SESSION_USER_KEY]!! }

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

            userDao.upsertUser(user.asEntity())
            sessionDao.upsertSession(
                session = LocalSession(
                    accessToken = token.accessToken,
                    userId = user.id,
                    refreshToken = token.refreshToken,
                    expiration = Clock.System.now().epochSeconds + token.expiresIn,
                )
            )

            dataStore.edit {
                it[SESSION_USER_KEY] = user.id
            }
        }
    }

    suspend fun changeSession(userId:String){
        withContext(Dispatchers.IO){
            dataStore.edit {
                it[SESSION_USER_KEY] = userId
            }
        }
    }

    companion object {
        val SESSION_USER_KEY = stringPreferencesKey("session_user_id")
    }
}