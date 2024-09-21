package com.elfen.letsred.ui.composables

import android.content.res.Configuration
import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.elfen.letsred.models.User
import com.elfen.letsred.ui.theme.AppTheme
import kotlinx.datetime.Instant

@Composable
fun DrawerProfile(
    modifier: Modifier = Modifier,
    user: User,
    onClickMore: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .padding(
                vertical = AppTheme.sizes.normal,
                horizontal = AppTheme.sizes.large,
            ),
        verticalArrangement = Arrangement.spacedBy(AppTheme.sizes.normal)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = user.icon,
                contentDescription = null,
                modifier = Modifier
                    .size(AppTheme.sizes.extraLarge2)
                    .clip(AppTheme.shapes.profile)
                    .background(AppTheme.colorScheme.secondaryContainer)
            )

            IconButton(
                onClick = { onClickMore() },
                modifier = Modifier.offset(x = AppTheme.sizes.small)
            ) {
                Icon(
                    Icons.Default.MoreHoriz,
                    contentDescription = null,
                    tint = AppTheme.colorScheme.secondaryText
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.sizes.extraSmall2)
        ) {
            if (!user.name.isNullOrEmpty()) {
                Text(user.name, style = AppTheme.typography.headingNormal)
            }
            Text(
                "@${user.username}",
                style = AppTheme.typography.bodyExtraSmall,
                color = AppTheme.colorScheme.secondaryText
            )
        }

        ProfileInfoText(user = user)
    }
}

@Preview
@Composable
private fun DrawerProfilePrev() {
    AppTheme {
        Surface(
            color = AppTheme.colorScheme.background,
            contentColor = AppTheme.colorScheme.onBackground,
            modifier = Modifier.width(284.dp)
        ) {
            DrawerProfile(
                user = dummyUser
            )
        }
    }
}

val dummyUser = User(
    id = "33o2ukxg",
    name = "Nizar Elfennani",
    username = "elfennani",
    karma = 24481,
    createdAt = Instant.fromEpochSeconds(1548694396),
    icon = Html.fromHtml(
        "https://styles.redditmedia.com/t5_vjcux/styles/profileIcon_snoo4eb7f2fb-0e85-4c4d-8ec2-0ee989b23566-headshot-f.png?width=256&amp;height=256&amp;crop=256:256,smart&amp;s=1e981ef29c72b2db0f07ad1f2e48b28b490d9f8f",
        Html.FROM_HTML_MODE_COMPACT
    ).toString(),
    isFollowed = true, banner = "invenire", over18 = true
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DrawerProfilePrevDark() {
    DrawerProfilePrev()
}