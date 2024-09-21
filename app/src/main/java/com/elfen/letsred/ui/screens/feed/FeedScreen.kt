package com.elfen.letsred.ui.screens.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.elfen.letsred.models.Session
import com.elfen.letsred.models.User
import com.elfen.letsred.ui.composables.HomeScaffold
import com.elfen.letsred.ui.screens.feed.composables.SessionSelector
import kotlinx.serialization.Serializable

@Serializable
object FeedRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    state: FeedUIState,
    onNavigate: (Any) -> Unit,
    onAddAccount: () -> Unit = {},
    onSetSession: (Session) -> Unit = {}
) {
    var sessionSelector by remember {
        mutableStateOf(false)
    }
    val user: User? = state.session.let {
        if (it is SessionUiState.Success)
            return@let it.user

        null
    }

    HomeScaffold(user = user, onClickMore = { sessionSelector = true }, onNavigate = onNavigate) {
        if (sessionSelector) {
            SessionSelector(
                sessionState = state.session,
                onDismissRequest = { sessionSelector = false },
                onAddAccount = onAddAccount,
                onClickSession = onSetSession
            )
        }

        Column(
            modifier = Modifier.padding(it)
        ) {
            Text("This is feed screen")
        }
    }
}

@Composable
fun FeedScreen(navController: NavController, viewModel: FeedViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    FeedScreen(
        state = state,
        onAddAccount = viewModel::onAddAccount,
        onSetSession = viewModel::setSession,
        onNavigate = navController::navigate
    )
}