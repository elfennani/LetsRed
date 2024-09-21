package com.elfen.letsred.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.elfen.letsred.ui.composables.ProfileHeader
import com.elfen.letsred.ui.theme.AppTheme
import kotlinx.serialization.Serializable

@Serializable
data class ProfileRoute(val username: String, val userId: String)

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onBack: () -> Unit = {}
) {
    Scaffold(
        containerColor = AppTheme.colorScheme.background,
        contentColor = AppTheme.colorScheme.onBackground
    ) { paddingValues ->
        when (state) {
            is ProfileUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(AppTheme.sizes.normal)
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Error, null, tint = AppTheme.colorScheme.error)
                    Spacer(Modifier.height(AppTheme.sizes.small))
                    Text(
                        state.message,
                        style = AppTheme.typography.labelLarge,
                        color = AppTheme.colorScheme.error
                    )
                }
            }

            ProfileUiState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(AppTheme.sizes.normal)
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            is ProfileUiState.Success -> Column {
                ProfileHeader(user = state.user)
                HorizontalDivider(color = AppTheme.colorScheme.secondarySurface, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProfileScreen(
        state = state,
        onBack = navController::popBackStack
    )
}