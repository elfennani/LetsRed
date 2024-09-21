package com.elfen.letsred.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ModeComment
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.elfen.letsred.models.Community
import com.elfen.letsred.models.Content
import com.elfen.letsred.models.ImageDetails
import com.elfen.letsred.models.ImageSource
import com.elfen.letsred.models.Post
import com.elfen.letsred.ui.theme.AppTheme
import com.elfen.letsred.utilities.decodeEntities
import com.elfen.letsred.utilities.readableTime
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.until

@Composable
fun PostCard(modifier: Modifier = Modifier, post: Post) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.sizes.extraSmall)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.sizes.extraSmall),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            AsyncImage(
                model = post.community.icon,
                contentDescription = post.community.name,
                modifier = Modifier
                    .size(AppTheme.sizes.large)
                    .clip(AppTheme.shapes.community)
                    .clickable { }
                    .background(AppTheme.colorScheme.secondaryContainer)
            )

            val annotatedString = buildAnnotatedString {
                withStyle(SpanStyle(color = AppTheme.colorScheme.onBackground)) {
                    append("r/${post.community.name}")
                }

                withStyle(SpanStyle(color = AppTheme.colorScheme.secondaryText)) {
                    append(
                        " • ${
                            post.createdAt.until(
                                Clock.System.now(),
                                DateTimeUnit.MILLISECOND,
                                TimeZone.currentSystemDefault()
                            ).readableTime()
                        }"
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(4.dp))
                    .clickable { }
                    .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    annotatedString,
                    style = AppTheme.typography.labelSmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            IconButton(
                onClick = {},
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = null,
                    tint = AppTheme.colorScheme.secondaryText
                )
            }
        }

        Text(post.title, style = AppTheme.typography.labelLarge)

        if (post.content != null) {
            PostContent(content = post.content)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.sizes.small)
        ) {
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(1.dp, AppTheme.colorScheme.secondaryContainer, CircleShape)
                    .height(IntrinsicSize.Min)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.sizes.extraSmall),
                    modifier = Modifier
                        .clickable { }
                        .padding(
                            vertical = AppTheme.sizes.extraSmall,
                            horizontal = AppTheme.sizes.small
                        )
                ) {
                    Icon(
                        Icons.Default.ArrowUpward,
                        contentDescription = null,
                        modifier = Modifier.size(AppTheme.sizes.medium)
                    )
                    Text(post.votes.toString(), style = AppTheme.typography.labelSmall)
                }
                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    color = AppTheme.colorScheme.secondaryContainer
                )
                Box(
                    modifier = Modifier
                        .clickable { }
                        .padding(AppTheme.sizes.extraSmall)
                        .padding(end = AppTheme.sizes.extraSmall2)
                ) {
                    Icon(
                        Icons.Default.ArrowDownward,
                        contentDescription = null,
                        modifier = Modifier.size(AppTheme.sizes.medium)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(1.dp, AppTheme.colorScheme.secondaryContainer, CircleShape)
                    .clickable { }
                    .height(IntrinsicSize.Min)
                    .padding(
                        vertical = AppTheme.sizes.extraSmall,
                        horizontal = AppTheme.sizes.small
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppTheme.sizes.extraSmall)
            ) {
                Icon(
                    Icons.Default.ModeComment,
                    contentDescription = null,
                    modifier = Modifier.size(AppTheme.sizes.medium)
                )
                Text(post.comments.toString(), style = AppTheme.typography.labelSmall)
            }
        }
    }
}

@Preview
@Composable
private fun PostCardPrevText() {
    AppTheme {
        Surface(
            color = AppTheme.colorScheme.background,
            contentColor = AppTheme.colorScheme.onBackground,
        ) {
            PostCard(
                modifier = Modifier
                    .clickable { }
                    .padding(AppTheme.sizes.normal),
                post = postText
            )
        }
    }
}

@Preview
@Composable
private fun PostCardPrevImage() {
    AppTheme {
        Surface(
            color = AppTheme.colorScheme.background,
            contentColor = AppTheme.colorScheme.onBackground,
        ) {
            PostCard(
                modifier = Modifier
                    .clickable { }
                    .padding(AppTheme.sizes.normal),
                post = postImage
            )
        }
    }
}

val postText = Post(
    id = "1fl9n6t",
    title = "Update to \"A54 was working perfectly fine, suddenly it broke\"",
    community = Community(
        id = "3m9xyg",
        name = "GalaxyA54",
        icon = "https://styles.redditmedia.com/t5_3m9xyg/styles/communityIcon_5pker08lrxna1.png?width=256&amp;s=185785a1a5b4c38da396b33616e41d4c5c4f49f6".decodeEntities()
    ),
    createdAt = Instant.fromEpochSeconds(1726831515),
    votes = 128,
    comments = 5803,
    content = Content.Text("&lt;!-- SC_OFF --&gt;&lt;div class=\"md\"&gt;&lt;p&gt;Link to previous post: &lt;a href=\"https://www.reddit.com/r/GalaxyA54/s/96gVyACBdB\"&gt;https://www.reddit.com/r/GalaxyA54/s/96gVyACBdB&lt;/a&gt;&lt;/p&gt;\n\n&lt;p&gt;I&amp;#39;m not sure when, but recently my mum sent her A54 off to a repair shop. It works perfectly now, but they didn&amp;#39;t tell her what part went wrong, just &amp;quot;faulty part replaced&amp;quot; I saw a comment on my original post that the power management unit may have died, which I believe. &lt;/p&gt;\n&lt;/div&gt;&lt;!-- SC_ON --&gt;".decodeEntities()),
    author = "creeping-fly349",
    authorId = "70lurwz4"
)

val postImage = Post(
    id = "1fl73qm",
    title = "Welcome to the club",
    community = Community(
        id = "2tsd7",
        name = "linuxmemes",
        icon = "https://b.thumbs.redditmedia.com/vGJ58JnAmWT8hytMzZ3-KJwCliSQ5_h744BparOe1IU.png".decodeEntities()
    ),
    createdAt = Instant.fromEpochSeconds(1726820656),
    votes = 141,
    comments = 17,
    content = Content.Images(
        images = listOf(
            ImageDetails(
                original = ImageSource(
                    url = "https://preview.redd.it/u2qkmf1ecxpd1.jpeg?auto=webp&amp;s=be1484e2126d90d6fdf05a6c255789b2deac292f".decodeEntities(),
                    width = 568,
                    height = 700
                ),
                resolutions = listOf(
                    ImageSource(
                        url = "https://preview.redd.it/u2qkmf1ecxpd1.jpeg?width=108&amp;crop=smart&amp;auto=webp&amp;s=d65645e84201065c0d02d0fd1e9cc7fedc7c4678".decodeEntities(),
                        width = 108,
                        height = 133
                    ),
                    ImageSource(
                        url = "https://preview.redd.it/u2qkmf1ecxpd1.jpeg?width=216&amp;crop=smart&amp;auto=webp&amp;s=b0423c36b62cfe24f5161dbd787b0b68313bbdf7".decodeEntities(),
                        width = 216,
                        height = 266
                    ),
                    ImageSource(
                        url = "https://preview.redd.it/u2qkmf1ecxpd1.jpeg?width=320&amp;crop=smart&amp;auto=webp&amp;s=db7bf73213162bdd4de069e0fd9bb35605b1fb75".decodeEntities(),
                        width = 320,
                        height = 394
                    )
                )
            ),
            ImageDetails(
                original = ImageSource(
                    url = "https://preview.redd.it/u2qkmf1ecxpd1.jpeg?auto=webp&amp;s=be1484e2126d90d6fdf05a6c255789b2deac292f".decodeEntities(),
                    width = 568,
                    height = 700
                ),
                resolutions = listOf(
                    ImageSource(
                        url = "https://preview.redd.it/u2qkmf1ecxpd1.jpeg?width=108&amp;crop=smart&amp;auto=webp&amp;s=d65645e84201065c0d02d0fd1e9cc7fedc7c4678".decodeEntities(),
                        width = 108,
                        height = 133
                    ),
                    ImageSource(
                        url = "https://preview.redd.it/u2qkmf1ecxpd1.jpeg?width=216&amp;crop=smart&amp;auto=webp&amp;s=b0423c36b62cfe24f5161dbd787b0b68313bbdf7".decodeEntities(),
                        width = 216,
                        height = 266
                    ),
                    ImageSource(
                        url = "https://preview.redd.it/u2qkmf1ecxpd1.jpeg?width=320&amp;crop=smart&amp;auto=webp&amp;s=db7bf73213162bdd4de069e0fd9bb35605b1fb75".decodeEntities(),
                        width = 320,
                        height = 394
                    )
                )
            )
        )
    ),
    author = "SweetTeaRex92",
    authorId = "5114encz"
)