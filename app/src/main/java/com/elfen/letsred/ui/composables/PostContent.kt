package com.elfen.letsred.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.elfen.letsred.models.Content
import com.elfen.letsred.ui.theme.AppTheme
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText

@Composable
fun PostContent(modifier: Modifier = Modifier, content: Content?) {
    when(content){
        is Content.CrossPost -> {
            Text(
                text = "CrossPost",
                style = AppTheme.typography.bodyExtraSmall,
                color = AppTheme.colorScheme.secondaryText,
            )
        }
        is Content.Images -> {
            val state = rememberPagerState { content.images.size }

            HorizontalPager(
                state,
                modifier = Modifier.clip(AppTheme.shapes.image).clickable {  }
            ) {index ->
                val image = content.images[index]

                AsyncImage(
                    model = image.original.url,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(image.original.width.toFloat()/image.original.height)
                        .background(AppTheme.colorScheme.secondaryContainer)
                )
            }
        }
        is Content.Text -> {
            val state = rememberRichTextState()
            val linkColor = AppTheme.colorScheme.secondaryText

            LaunchedEffect(Unit) {
                state.setHtml(content.text)
                state.config.linkColor = linkColor
            }

            RichText(
                state,
                style = AppTheme.typography.bodyExtraSmall,
                color = AppTheme.colorScheme.secondaryText,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
        is Content.Video -> {
            Text(
                text = "Video",
                style = AppTheme.typography.bodyExtraSmall,
                color = AppTheme.colorScheme.secondaryText,
            )
        }

        else -> {}
    }
}

@Preview
@Composable
private fun PostContentPrevImage() {
    AppTheme {
        Surface(
            color = AppTheme.colorScheme.background,
            contentColor = AppTheme.colorScheme.onBackground,
            modifier = Modifier.width(360.dp)
        ) {
            PostContent(content = postImage.content)
        }
    }
}

@Preview
@Composable
private fun PostContentPrevText() {
    AppTheme {
        Surface(
            color = AppTheme.colorScheme.background,
            contentColor = AppTheme.colorScheme.onBackground,
            modifier = Modifier.width(360.dp)
        ) {
            PostContent(content = postText.content)
        }
    }
}