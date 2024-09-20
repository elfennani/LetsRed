package com.elfen.letsred.ui.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.elfen.letsred.models.User
import com.elfen.letsred.ui.theme.AppTheme
import com.elfen.letsred.utilities.readableTime
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.until

@Composable
fun ProfileInfoText(
    modifier: Modifier = Modifier,
    user: User
) {
    val createdAtString by remember {
        derivedStateOf {
            user.createdAt.until(
                Clock.System.now(),
                DateTimeUnit.MILLISECOND,
                TimeZone.currentSystemDefault()
            ).readableTime(true)
        }
    }

    val info = buildAnnotatedString {
        val normalStyle = AppTheme.typography.bodyExtraSmall
            .toSpanStyle()
            .copy(color = AppTheme.colorScheme.secondaryText)
        val boldStyle = AppTheme.typography.labelSmall
            .toSpanStyle()
            .copy(color = AppTheme.colorScheme.onSecondaryContainer)

        withStyle(boldStyle) {
            append("%,d".format(user.karma))
        }
        withStyle(normalStyle) {
            append(" Karma")
        }
        withStyle(boldStyle) {
            append(" • ")
            append(createdAtString)
        }
        withStyle(normalStyle) {
            append(" old")
        }
    }

    Text(info, modifier = modifier)
}