package com.elfen.letsred.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.elfen.letsred.ui.theme.AppTheme
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.ImageData
import com.mohamedrejeb.richeditor.model.ImageLoader
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText

@OptIn(ExperimentalRichTextApi::class)
@Composable
fun HtmlBody(modifier: Modifier = Modifier, body: String) {
    val state = rememberRichTextState()
    val linkColor = AppTheme.colorScheme.primary
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        state.setMarkdown(body)
        state.config.linkColor = linkColor
    }

    RichText(
        state,
        style = AppTheme.typography.bodySmall,
        imageLoader = object : ImageLoader {
            @Composable
            override fun load(model: Any): ImageData? {
                val painter = rememberAsyncImagePainter(model = model)

                var imageData by remember {
                    mutableStateOf<ImageData?>(null)
                }

                LaunchedEffect(painter.state) {
                    painter.state.collect { state ->
                        imageData =
                            if (state is AsyncImagePainter.State.Success)
                                ImageData(
                                    painter = state.painter
                                )
                            else
                                null
                    }
                }

                return imageData
            }
        }
    )
}