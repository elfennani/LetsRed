package com.elfen.letsred.ui.screens.authenticate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.elfen.letsred.ui.theme.AppTheme

object AuthenticateRoute

@Composable
private fun AuthenticateScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(Modifier.height(AppTheme.sizes.normal))
        Text("Authenticating...", style = AppTheme.typography.labelLarge)
    }
}

@Composable
fun AuthenticateScreen(navController: NavController) {
    Scaffold(
        containerColor = AppTheme.colorScheme.background,
        contentColor = AppTheme.colorScheme.onBackground,
    ) {
        AuthenticateScreen(modifier = Modifier.padding(it))
    }
}

@Preview
@Composable
private fun AuthenticateScreenPrev() {
    AppTheme {
        AuthenticateScreen()
    }
}