package com.elfen.letsred.ui.screens.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.elfen.letsred.ui.composables.HomeScaffold
import com.elfen.letsred.ui.composables.dummyUser
import kotlinx.serialization.Serializable

@Serializable
object FeedRoute

@Composable
fun FeedScreen() {
    HomeScaffold(user = dummyUser) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text("This is feed screen")
        }
    }
}

@Composable
fun FeedScreen(navController: NavController) {
    FeedScreen()
}