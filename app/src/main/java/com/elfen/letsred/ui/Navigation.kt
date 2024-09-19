package com.elfen.letsred.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elfen.letsred.ui.screens.authenticate.AuthenticateRoute
import com.elfen.letsred.ui.screens.authenticate.AuthenticateScreen
import com.elfen.letsred.ui.screens.login.LoginRoute
import com.elfen.letsred.ui.screens.login.LoginScreen

@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = LoginRoute
    ) {
        composable<LoginRoute> {
            LoginScreen(navController = navHostController)
        }
        composable<AuthenticateRoute> {
            AuthenticateScreen(navHostController)
        }
    }
}