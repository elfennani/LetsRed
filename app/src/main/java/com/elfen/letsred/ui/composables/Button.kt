package com.elfen.letsred.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfen.letsred.ui.theme.AppTheme
import androidx.compose.material3.Button as NativeButton

@Composable
fun Button(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    NativeButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = AppTheme.colorScheme.primary,
            contentColor = AppTheme.colorScheme.onPrimary
        ),
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CompositionLocalProvider(
                    LocalTextStyle provides AppTheme.typography.labelNormal
                ) { content() }
            }
        },
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(16.dp)
    )
}

@Preview
@Composable
private fun ButtonPrev() {
    AppTheme {
        Surface {
            Column(Modifier.padding(16.dp)) {
                Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(text = "Login to an existing account")
                }
            }
        }
    }
}