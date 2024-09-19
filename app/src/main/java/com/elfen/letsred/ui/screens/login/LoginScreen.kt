package com.elfen.letsred.ui.screens.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.elfen.letsred.ui.composables.Button
import com.elfen.letsred.ui.theme.AppTheme

object LoginRoute

@Composable
private fun LoginScreen(
    onLogin: () -> Unit = {}
) {
    Scaffold(
        containerColor = AppTheme.colorScheme.background,
        contentColor = AppTheme.colorScheme.onBackground
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(AppTheme.sizes.large)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val title = buildAnnotatedString {
                    withStyle(SpanStyle(color = AppTheme.colorScheme.onSecondary)) {
                        append("Lets")
                    }
                    withStyle(SpanStyle(color = AppTheme.colorScheme.primary)) {
                        append("Red")
                    }
                }
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = AppTheme.typography.headingLarge,
                    fontSize = 64.sp,
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Login,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Text(text = "Login to an existing account")
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    LoginScreen(
        onLogin = { /* TODO */ }
    )
}

@Preview
@Composable
private fun LoginScreenPreview() {
    AppTheme {
        LoginScreen()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LoginScreenPreviewDark() {
    AppTheme {
        LoginScreen()
    }
}