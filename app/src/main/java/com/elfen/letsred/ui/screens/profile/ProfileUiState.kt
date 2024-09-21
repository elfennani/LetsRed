package com.elfen.letsred.ui.screens.profile

import com.elfen.letsred.models.User

sealed interface ProfileUiState {
    data object Loading: ProfileUiState
    data class Success(val user: User): ProfileUiState
    data class Error(val message: String): ProfileUiState
}