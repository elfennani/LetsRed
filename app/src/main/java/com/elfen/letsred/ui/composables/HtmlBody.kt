package com.elfen.letsred.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.elfen.letsred.ui.theme.AppTheme
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText

@Composable
fun HtmlBody(modifier: Modifier = Modifier, body: String) {
    val state = rememberRichTextState()
    val linkColor = AppTheme.colorScheme.primary

    LaunchedEffect(Unit) {
        state.setHtml(body)
        state.config.linkColor = linkColor
    }

    RichText(state, style = AppTheme.typography.bodySmall)
}