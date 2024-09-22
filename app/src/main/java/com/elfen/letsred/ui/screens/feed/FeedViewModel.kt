package com.elfen.letsred.ui.screens.feed

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfen.letsred.MainActivity
import com.elfen.letsred.data.repository.FeedRepository
import com.elfen.letsred.data.repository.SessionRepository
import com.elfen.letsred.models.FeedSource
import com.elfen.letsred.models.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val feedRepository: FeedRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val sessions = sessionRepository.sessions
    private val activeSession = sessionRepository.activeSession
    val posts = feedRepository.feedPagerByQuery(FeedSource.Best)

    val state = combine(sessions, activeSession) { sessions, activeSession ->
        val currentUser = sessions.find { it.user.id == activeSession }?.user

        val sessionUiState =
            if (currentUser == null) SessionUiState.Error("Something wrong happened")
            else SessionUiState.Success(currentUser, sessions)

        FeedUIState(session = sessionUiState)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        FeedUIState()
    )


    fun setSession(session: Session) {
        viewModelScope.launch {
            sessionRepository.changeSession(session.user.id)
        }.invokeOnCompletion {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
            Runtime.getRuntime().exit(0)
        }
    }

    fun onAddAccount() {
        sessionRepository.initiateAuthentication()
    }
}