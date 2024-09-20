package com.elfen.letsred.ui

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.elfen.letsred.data.local.dataStore
import com.elfen.letsred.data.repository.SessionRepository
import com.elfen.letsred.ui.screens.authenticate.AuthenticateScreen
import com.elfen.letsred.ui.screens.feed.FeedRoute
import com.elfen.letsred.ui.screens.feed.FeedScreen
import com.elfen.letsred.ui.screens.login.LoginRoute
import com.elfen.letsred.ui.screens.login.LoginScreen
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

@Composable
fun Navigation(navHostController: NavHostController) {
    val context = LocalContext.current
    val sessionId = runBlocking {
        context.dataStore.data.map { it[SessionRepository.SESSION_USER_KEY] }.firstOrNull()
    }

    NavHost(
        navController = navHostController,
        startDestination = if (sessionId.isNullOrEmpty()) LoginRoute else FeedRoute
    ) {
        composable<LoginRoute> {
            LoginScreen(navController = navHostController)
        }
        composable(
            "authenticate/{code}/{state}",
            deepLinks = listOf(
                navDeepLink{
                    uriPattern = "letsred://validate?state={state}&code={code}#_"
                    action = Intent.ACTION_VIEW
                }
            )
        ) {
            AuthenticateScreen(navHostController)
        }
        composable<FeedRoute> {
            FeedScreen(navHostController)
        }
    }
}