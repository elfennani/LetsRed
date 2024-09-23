package com.elfen.letsred.ui

import android.content.Intent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.elfen.letsred.data.local.dataStore
import com.elfen.letsred.data.repository.SessionRepository
import com.elfen.letsred.ui.screens.authenticate.AuthenticateScreen
import com.elfen.letsred.ui.screens.feed.FeedRoute
import com.elfen.letsred.ui.screens.feed.FeedScreen
import com.elfen.letsred.ui.screens.login.LoginRoute
import com.elfen.letsred.ui.screens.login.LoginScreen
import com.elfen.letsred.ui.screens.post.PostRoute
import com.elfen.letsred.ui.screens.post.PostScreen
import com.elfen.letsred.ui.screens.profile.ProfileRoute
import com.elfen.letsred.ui.screens.profile.ProfileScreen
import com.elfen.letsred.ui.screens.saved.SavedRoute
import com.elfen.letsred.ui.screens.saved.SavedScreen
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt

const val ANIM_DURATION_MILLIS = 150

@Composable
fun Navigation(navHostController: NavHostController) {
    val spec = remember{ tween<Float>(ANIM_DURATION_MILLIS, easing = FastOutSlowInEasing) }
    val specInt = remember { tween<IntOffset>(ANIM_DURATION_MILLIS, easing = FastOutSlowInEasing) }
    val context = LocalContext.current
    val sessionId = runBlocking {
        context.dataStore.data.map { it[SessionRepository.SESSION_USER_KEY] }.firstOrNull()
    }

    NavHost(
        navController = navHostController,
        startDestination = if (sessionId.isNullOrEmpty()) LoginRoute else FeedRoute,
        enterTransition = {
            fadeIn() + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(ANIM_DURATION_MILLIS),
            ) + scaleIn(initialScale = 0.9f)
        },
        exitTransition = {
            fadeOut(animationSpec = tween(ANIM_DURATION_MILLIS)) + scaleOut(
                targetScale = 0.75f
            )
        },
        popEnterTransition = {
            fadeIn(animationSpec = spec) +
                    scaleIn(
                        initialScale = 0.9f,
                        animationSpec = spec
                    ) +
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        initialOffset = { (it * 0.15f).roundToInt() },
                        animationSpec = specInt
                    )
        },
        popExitTransition = {
            fadeOut(animationSpec = spec) +
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        targetOffset = { (it * 0.15f).roundToInt() },
                        animationSpec = specInt
                    ) +
                    scaleOut(
                        targetScale = 0.85f,
                        animationSpec = spec
                    )
        },
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

        composable<ProfileRoute> {
            ProfileScreen(navHostController)
        }

        composable<SavedRoute> {
            SavedScreen(navHostController)
        }

        composable<PostRoute> {
            PostScreen(navHostController, it.toRoute())
        }
    }
}