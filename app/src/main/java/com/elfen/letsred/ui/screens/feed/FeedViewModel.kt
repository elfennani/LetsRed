package com.elfen.letsred.ui.screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfen.letsred.data.repository.SessionRepository
import com.elfen.letsred.models.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
) : ViewModel() {
    private val sessions = sessionRepository.sessions
    private val activeSession = sessionRepository.activeSession

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
        }
    }

    fun onAddAccount() {
        sessionRepository.initiateAuthentication()
    }
}