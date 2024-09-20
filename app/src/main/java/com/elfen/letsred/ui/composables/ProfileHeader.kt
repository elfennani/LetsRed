package com.elfen.letsred.ui.composables

import android.content.res.Configuration
import android.text.Html
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key.Companion.H
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.elfen.letsred.models.User
import com.elfen.letsred.ui.theme.AppTheme
import com.elfen.letsred.utilities.decodeEntities
import kotlinx.datetime.Instant

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    user: User,
    onFollow: () -> Unit = {},
    onUnfollow: () -> Unit = {}
) {
    val isFollowed by remember {
        derivedStateOf { user.isFollowed }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.sizes.extraLarge3 / 2)
    ) {
        Box {
            AsyncImage(
                model = user.banner,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppTheme.sizes.extraLarge3)
                    .background(AppTheme.colorScheme.secondarySurface)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.sizes.large)
                    .offset(y = AppTheme.sizes.extraLarge3 / 2),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                AsyncImage(
                    model = user.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(AppTheme.sizes.extraLarge3)
                        .clip(AppTheme.shapes.community)
                        .background(AppTheme.colorScheme.secondaryContainer)
                )

                FollowButton(isFollowed, onFollow, onUnfollow)
            }
        }

        Column(
            modifier = Modifier.padding(
                vertical = AppTheme.sizes.normal,
                horizontal = AppTheme.sizes.large
            ),
            verticalArrangement = Arrangement.spacedBy(AppTheme.sizes.normal)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.sizes.extraSmall2)
            ) {
                Text(user.name, style = AppTheme.typography.headingNormal)
                Text(
                    "@${user.username}",
                    style = AppTheme.typography.bodyExtraSmall,
                    color = AppTheme.colorScheme.secondaryText
                )
            }

            ProfileInfoText(user = user)
        }
    }
}

@Composable
private fun FollowButton(isFollowed: Boolean, onFollow: () -> Unit, onUnfollow: () -> Unit) {
    AnimatedContent(targetState = isFollowed, label = "isFollowed") { isFollowed ->
        if (!isFollowed) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onFollow() }
                    .background(AppTheme.colorScheme.primary)
                    .padding(
                        horizontal = AppTheme.sizes.small,
                        vertical = AppTheme.sizes.extraSmall2
                    )
            ) {
                Text(
                    "Follow",
                    style = AppTheme.typography.labelNormal,
                    color = AppTheme.colorScheme.onPrimary
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onUnfollow() }
                    .border(1.dp, AppTheme.colorScheme.primary, CircleShape)
                    .padding(
                        horizontal = AppTheme.sizes.small,
                        vertical = AppTheme.sizes.extraSmall2
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppTheme.sizes.extraSmall)
            ) {
                Icon(
                    Icons.Default.Check,
                    null,
                    modifier = Modifier.size(AppTheme.sizes.normal),
                    tint = AppTheme.colorScheme.primary
                )
                Text(
                    "Followed",
                    style = AppTheme.typography.labelNormal,
                    color = AppTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProfileHeaderPrev(isFollowed: Boolean = false) {
    AppTheme {
        Surface(
            color = AppTheme.colorScheme.background,
            contentColor = AppTheme.colorScheme.onBackground,
            modifier = Modifier.width(320.dp)
        ) {
            ProfileHeader(
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
                    isFollowed = isFollowed,
                    banner = "https://styles.redditmedia.com/t5_4xrrob/styles/profileBanner_89ckm9wnqo8c1.jpeg?width=1280&amp;height=384&amp;crop=1280:384,smart&amp;s=3cf2753d1cf85e8f406b75f81ac29c643c2ed1d0".decodeEntities(),
                )
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProfileHeaderPrevDark() {
    ProfileHeaderPrev(true)
}