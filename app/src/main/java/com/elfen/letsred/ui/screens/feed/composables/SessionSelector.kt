package com.elfen.letsred.ui.screens.feed.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.elfen.letsred.models.Session
import com.elfen.letsred.ui.composables.Button
import com.elfen.letsred.ui.composables.ProfileListItem
import com.elfen.letsred.ui.screens.feed.SessionUiState
import com.elfen.letsred.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionSelector(
    sessionState: SessionUiState,
    onDismissRequest: () -> Unit,
    onAddAccount: () -> Unit,
    onClickSession: (Session) -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState()
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        containerColor = AppTheme.colorScheme.background,
        contentColor = AppTheme.colorScheme.onBackground,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                height = AppTheme.sizes.extraSmall2,
                width = AppTheme.sizes.extraLarge,
                color = AppTheme.colorScheme.secondaryContainer
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = AppTheme.sizes.normal)
                .padding(bottom = AppTheme.sizes.normal),
            verticalArrangement = Arrangement.spacedBy(AppTheme.sizes.normal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Accounts",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = AppTheme.typography.headingNormal
            )

            when (sessionState) {
                is SessionUiState.Loading -> LinearProgressIndicator()

                is SessionUiState.Error -> Text(
                    "Failed to load sessions",
                    style = AppTheme.typography.bodyNormal,
                    color = AppTheme.colorScheme.error
                )

                is SessionUiState.Success -> {
                    sessionState.sessions?.forEach { session ->
                        ProfileListItem(
                            session = session,
                            selected = session.user.id == sessionState.user.id,
                            modifier = Modifier
                                .clip(AppTheme.shapes.profile)
                                .clickable { onClickSession(session) }
                                .padding(end = AppTheme.sizes.small)
                        )
                    }
                }
            }

            Button(
                onClick = onAddAccount,
                modifier = Modifier.fillMaxWidth(),
                enabled = sessionState is SessionUiState.Success
            ) {
                Icon(Icons.Default.PersonAdd, null)
                Text("Add Account")
            }
        }
    }
}