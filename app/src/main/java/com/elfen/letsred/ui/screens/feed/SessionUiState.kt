package com.elfen.letsred.ui.screens.feed

import com.elfen.letsred.models.Session
import com.elfen.letsred.models.User

sealed interface SessionUiState {
    data object Loading: SessionUiState
    data class Success(val user: User, val sessions: List<Session>): SessionUiState
    data class Error(val message: String): SessionUiState
}