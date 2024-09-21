package com.elfen.letsred.ui.composables

import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.elfen.letsred.models.Session
import com.elfen.letsred.models.User
import com.elfen.letsred.ui.theme.AppTheme
import kotlinx.datetime.Instant

@Composable
fun ProfileListItem(
    modifier: Modifier = Modifier,
    icon: String?,
    name: String?,
    username: String,
    selected: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.sizes.normal)
    ) {
        AsyncImage(
            model = icon,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(AppTheme.shapes.profile)
                .background(AppTheme.colorScheme.secondaryContainer)
        )
        Column(modifier = Modifier.weight(1f)) {
            if(!name.isNullOrEmpty()){
                Text(
                    text = name,
                    style = AppTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                "@$username",
                style = AppTheme.typography.bodyExtraSmall,
                color = AppTheme.colorScheme.secondaryText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = AppTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ProfileListItem(modifier: Modifier = Modifier, user: User, selected: Boolean = false) {
    ProfileListItem(modifier, user.icon, user.name, user.username, selected)
}

@Composable
fun ProfileListItem(modifier: Modifier = Modifier, session: Session, selected: Boolean = false) {
    ProfileListItem(modifier, session.user, selected)
}

@Preview
@Composable
private fun ProfileListItemPrev() {
    AppTheme {
        Surface(
            color = AppTheme.colorScheme.background,
            contentColor = AppTheme.colorScheme.onBackground,
            modifier = Modifier.width(320.dp)
        ) {
            ProfileListItem(
                user = User(
                    id = "33o2ukxg",
                    name = "Nizar Elfennani",
                    username = "elfennani",
                    karma = 24481,
                    createdAt = Instant.fromEpochSeconds(1548694396),
                    icon = Html.fromHtml(
                        "https://styles.redditmedia.com/t5_vjcux/styles/profileIcon_snoo4eb7f2fb-0e85-4c4d-8ec2-0ee989b23566-headshot-f.png?width=256&amp;height=256&amp;crop=256:256,smart&amp;s=1e981ef29c72b2db0f07ad1f2e48b28b490d9f8f",
                        Html.FROM_HTML_MODE_COMPACT
                    ).toString(),
                    isFollowed = false,
                    banner = "feugait",
                    over18 = false
                )
            )
        }
    }
}