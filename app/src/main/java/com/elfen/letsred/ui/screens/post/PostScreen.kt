package com.elfen.letsred.ui.screens.post

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.elfen.letsred.models.Comment
import com.elfen.letsred.models.Post
import com.elfen.letsred.ui.composables.Button
import com.elfen.letsred.ui.composables.HtmlBody
import com.elfen.letsred.ui.composables.PostCard
import com.elfen.letsred.ui.theme.AppTheme
import kotlinx.serialization.Serializable
import androidx.compose.material3.Button as NativeButton

@Serializable
data class PostRoute(val id: String, val subreddit: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    route: PostRoute,
    state: PostUiState,
    onBack: () -> Unit = {}
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("r/${route.subreddit}", style = AppTheme.typography.labelLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
                    containerColor = AppTheme.colorScheme.background,
                    titleContentColor = AppTheme.colorScheme.onBackground,
                    navigationIconContentColor = AppTheme.colorScheme.onBackground
                ),
            )
        },
        containerColor = AppTheme.colorScheme.secondarySurface,
        contentColor = AppTheme.colorScheme.onBackground
    ) { paddingValues ->
        if (state.post != null) {
            LazyColumn(
                contentPadding = paddingValues,
            ) {
                item {
                    Column {
                        PostCard(
                            modifier = Modifier
                                .clickable { }
                                .background(AppTheme.colorScheme.background)
                                .padding(AppTheme.sizes.normal),
                            post = state.post
                        )
                        HorizontalDivider(
                            color = AppTheme.colorScheme.secondarySurface,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                items(state.comments) { comment ->
                    when (comment) {
                        is Comment.Content -> {
                            if (comment.depth == 0) {
                                Spacer(Modifier.height(AppTheme.sizes.extraSmall))
                            }
                            Column(
                                modifier = Modifier
                                    .background(AppTheme.colorScheme.background)
                                    .padding(AppTheme.sizes.normal)
                                    .padding(start = AppTheme.sizes.extraSmall * comment.depth),
                                verticalArrangement = Arrangement.spacedBy(AppTheme.sizes.extraSmall)
                            ) {
                                Text(
                                    "u/${comment.authorUsername}",
                                    style = AppTheme.typography.labelNormal
                                )
                                HtmlBody(body = comment.body)
                            }
                        }

                        is Comment.More -> {
                            if (comment.depth == 0) {
                                Spacer(Modifier.height(AppTheme.sizes.extraSmall))
                            }
                            Column(
                                modifier = Modifier
                                    .background(AppTheme.colorScheme.background)
                                    .padding(AppTheme.sizes.normal)
                                    .padding(start = AppTheme.sizes.extraSmall * comment.depth),
                                verticalArrangement = Arrangement.spacedBy(AppTheme.sizes.extraSmall)
                            ) {
                                NativeButton(
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors().copy(
                                        containerColor = AppTheme.colorScheme.primary,
                                        contentColor = AppTheme.colorScheme.onPrimary
                                    )
                                ) {
                                    Text("More", style = AppTheme.typography.labelNormal)
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator(color = AppTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun PostScreen(
    navController: NavController,
    postRoute: PostRoute,
    viewModel: PostViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PostScreen(
        state = state,
        route = postRoute,
        onBack = navController::popBackStack
    )
}