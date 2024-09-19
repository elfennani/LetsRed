package com.elfen.letsred.ui.theme


import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfennani.letsred.ui.theme.Inter
import com.elfennani.letsred.ui.theme.InterTight

private val lightColorScheme = AppColorScheme(
    primary = Primary60,
    onPrimary = Primary10,
    primaryContainer = Primary20,
    onPrimaryContainer = Primary80,
    primarySurface = Primary10,

    secondary = Grey60,
    onSecondary = Grey90,
    secondaryContainer = Grey20,
    onSecondaryContainer = Grey80,
    secondarySurface = Grey10,
    secondaryText = Grey40,

    background = Color.White,
    onBackground = BodyLight,

    error = Error,
    success = Success,
    warning = Warning

)

private val darkColorScheme = AppColorScheme(
    primary = Primary40,
    onPrimary = Primary10,
    primaryContainer = Primary80,
    onPrimaryContainer = Primary20,
    primarySurface = Primary90,

    secondary = Grey40,
    onSecondary = Grey10,
    secondaryContainer = Grey80,
    onSecondaryContainer = Grey20,
    secondarySurface = Grey90,
    secondaryText = Grey60,

    background = Color.Black,
    onBackground = BodyDark,

    error = Error,
    success = Success,
    warning = Warning

)

private val typography = AppTypography(
    headingLarge = TextStyle(
        fontFamily = InterTight,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 33.sp
    ),
    headingNormal = TextStyle(
        fontFamily = InterTight,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 27.sp
    ),
    headingSmall = TextStyle(
        fontFamily = InterTight,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    bodyNormal = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 19.sp
    ),
    bodyExtraSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.24.sp
    ),
    labelNormal = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.24.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.24.sp
    ),
)

private val shape = AppShape(
    button = RoundedCornerShape(12.dp),
    profile = RoundedCornerShape(16.dp),
    community = CircleShape,
    image = RoundedCornerShape(8.dp)
)

private val size = AppSize(
    extraLarge5 = 256.dp,
    extraLarge4 = 128.dp,
    extraLarge3 = 96.dp,
    extraLarge2 = 64.dp,
    extraLarge = 32.dp,
    large = 24.dp,
    medium = 18.dp,
    normal = 16.dp,
    small = 12.dp,
    extraSmall = 8.dp,
    extraSmall2 = 4.dp
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme
    val rippleIndication = rememberRipple()

    MaterialTheme {
        CompositionLocalProvider(
            LocalAppColorScheme provides colorScheme,
            LocalAppTypography provides typography,
            LocalAppShape provides shape,
            LocalAppSize provides size,
            LocalIndication provides rippleIndication,
            content = content
        )
    }

}

object AppTheme {

    val colorScheme: AppColorScheme
        @Composable get() = LocalAppColorScheme.current

    val typography: AppTypography
        @Composable get() = LocalAppTypography.current

    val shapes: AppShape
        @Composable get() = LocalAppShape.current

    val sizes: AppSize
        @Composable get() = LocalAppSize.current

}