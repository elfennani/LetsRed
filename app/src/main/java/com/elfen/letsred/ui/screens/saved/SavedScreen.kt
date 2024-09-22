package com.elfen.letsred.ui.screens.saved

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.elfen.letsred.models.Post
import com.elfen.letsred.ui.composables.PostCard
import com.elfen.letsred.ui.theme.AppTheme
import kotlinx.serialization.Serializable

@Serializable
data class SavedRoute(val username: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(
    lazyPosts: LazyPagingItems<Post>,
    onBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Saved Posts", style = AppTheme.typography.labelLarge) },
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
        containerColor = AppTheme.colorScheme.background,
        contentColor = AppTheme.colorScheme.onBackground
    ) {
        LazyColumn(contentPadding = it) {
            items(
                count = lazyPosts.itemCount,
                key = lazyPosts.itemKey { post -> post.id }
            ) { index ->
                val post = lazyPosts[index]

                if (post != null) {
                    PostCard(
                        modifier = Modifier
                            .clickable { }
                            .padding(AppTheme.sizes.normal),
                        post = post
                    )
                }
            }
        }
    }
}

@Composable
fun SavedScreen(navController: NavController, viewModel: SavedViewModel = hiltViewModel()) {
    SavedScreen(
        lazyPosts = viewModel.lazyPosts.collectAsLazyPagingItems(),
        onBack = navController::popBackStack
    )
}