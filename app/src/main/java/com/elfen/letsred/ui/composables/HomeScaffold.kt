package com.elfen.letsred.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfen.letsred.models.User
import com.elfen.letsred.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScaffold(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Open),
    user: User,
    onClickMore: () -> Unit = {},
    onNavigate: (Any) -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = AppTheme.colorScheme.background,
                drawerContentColor = AppTheme.colorScheme.onBackground,
                modifier = Modifier.width(AppTheme.sizes.extraLarge5)
            ) {
                DrawerProfile(
                    user = user,
                    modifier = Modifier
                        .clickable { /* TODO: navigate to profile */ }
                        .padding(top = AppTheme.sizes.large),
                    onClickMore = onClickMore
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.sizes.large),
                    color = AppTheme.colorScheme.secondaryContainer
                )
                Column(
                    modifier = Modifier.padding(vertical = AppTheme.sizes.extraSmall).weight(1f),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.sizes.extraSmall2)
                ) {
                    DrawerOption(
                        label = "Profile",
                        icon = Icons.Default.Person,
                        modifier = Modifier.clickable { /* TODO */ }
                    )
                    DrawerOption(
                        label = "Notifications",
                        icon = Icons.Default.Notifications,
                        modifier = Modifier.clickable { /* TODO */ }
                    )
                    DrawerOption(
                        label = "Saved",
                        icon = Icons.Default.Bookmarks,
                        modifier = Modifier.clickable { /* TODO */ }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.sizes.large),
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier.offset(
                            x = AppTheme.sizes.small,
                            y = AppTheme.sizes.small
                        )
                    ) {
                        Icon(Icons.Default.WbSunny, null, tint = AppTheme.colorScheme.onSecondaryContainer)
                    }
                }
            }
        }
    ) {
        Scaffold(
            containerColor = AppTheme.colorScheme.background,
            contentColor = AppTheme.colorScheme.onBackground,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(AppTheme.sizes.extraSmall2),
                            modifier = Modifier
                                .clip(RoundedCornerShape(AppTheme.sizes.extraSmall))
                                .clickable { /*TODO*/ }
                                .padding(
                                    vertical = AppTheme.sizes.extraSmall,
                                    horizontal = AppTheme.sizes.small
                                )
                        ) {
                            Text(
                                "Feed",
                                style = AppTheme.typography.labelLarge,
                                color = AppTheme.colorScheme.onSecondary
                            )
                            Icon(
                                Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = AppTheme.colorScheme.onSecondary
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* TODO: navigate to search screen */ }) {
                            Icon(Icons.Default.Search, contentDescription = null)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
                        containerColor = AppTheme.colorScheme.background,
                        actionIconContentColor = AppTheme.colorScheme.onSecondary,
                        navigationIconContentColor = AppTheme.colorScheme.onSecondary
                    )
                )
            }
        ) {
            content(it)
        }
    }
}

@Composable
fun DrawerOption(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.sizes.small),
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = AppTheme.sizes.large,
                vertical = AppTheme.sizes.normal
            ),
    ) {
        Icon(icon, null, tint = AppTheme.colorScheme.onSecondaryContainer)
        Text(
            label,
            style = AppTheme.typography.headingSmall,
            color = AppTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Preview
@Composable
private fun HomeScaffoldPrev() {
    AppTheme {
        HomeScaffold(user = dummyUser){}
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScaffoldPrevDark() {
    AppTheme {
        HomeScaffold(user = dummyUser){}
    }
}