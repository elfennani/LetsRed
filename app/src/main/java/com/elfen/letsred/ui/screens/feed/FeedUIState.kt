package com.elfen.letsred.ui.screens.feed

import com.elfen.letsred.models.Session

data class FeedUIState(
    val session: SessionUiState = SessionUiState.Loading,
)
