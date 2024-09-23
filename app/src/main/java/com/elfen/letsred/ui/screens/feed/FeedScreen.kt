package com.elfen.letsred.ui.screens.feed

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.elfen.letsred.models.Post
import com.elfen.letsred.models.Session
import com.elfen.letsred.models.User
import com.elfen.letsred.ui.composables.HomeScaffold
import com.elfen.letsred.ui.composables.PostCard
import com.elfen.letsred.ui.screens.feed.composables.SessionSelector
import com.elfen.letsred.ui.screens.post.PostRoute
import com.elfen.letsred.ui.theme.AppTheme
import kotlinx.serialization.Serializable

@Serializable
object FeedRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    state: FeedUIState,
    onNavigate: (Any) -> Unit,
    onAddAccount: () -> Unit = {},
    onSetSession: (Session) -> Unit = {},
    postsByPaging: LazyPagingItems<Post>
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

        LazyColumn(contentPadding = it) {
            items(
                count = postsByPaging.itemCount,
                key = postsByPaging.itemKey { post -> post.id }
            ) { index ->
                val post = postsByPaging[index]

                if (post != null) {
                    PostCard(
                        modifier = Modifier
                            .clickable { onNavigate(PostRoute(post.id, post.community.name)) }
                            .padding(AppTheme.sizes.normal),
                        post = post
                    )
                }
            }
        }
    }
}

@Composable
fun FeedScreen(navController: NavController, viewModel: FeedViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val paging = viewModel.posts.collectAsLazyPagingItems()

    FeedScreen(
        state = state,
        postsByPaging = paging,
        onAddAccount = viewModel::onAddAccount,
        onSetSession = viewModel::setSession,
        onNavigate = navController::navigate
    )
}