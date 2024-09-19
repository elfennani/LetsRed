package com.elfen.letsred.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class AppColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val primarySurface: Color,

    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val secondarySurface: Color,

    val background: Color,
    val onBackground: Color,

    val error: Color,
    val success: Color,

    val warning: Color,
)

data class AppTypography(
    val headingLarge: TextStyle,
    val headingNormal: TextStyle,
    val headingSmall: TextStyle,
    val bodyLarge: TextStyle,
    val bodyNormal: TextStyle,
    val bodySmall: TextStyle,
    val bodyExtraSmall: TextStyle,
    val labelLarge: TextStyle,
    val labelNormal: TextStyle,
    val labelSmall: TextStyle,
)

data class AppShape(
    val button: Shape,
    val profile: Shape,
    val community: Shape,
    val image: Shape
)

data class AppSize(
    val extraLarge5: Dp,
    val extraLarge4: Dp,
    val extraLarge3: Dp,
    val extraLarge2: Dp,
    val extraLarge: Dp,
    val large: Dp,
    val medium: Dp,
    val normal: Dp,
    val small: Dp,
    val extraSmall: Dp,
    val extraSmall2: Dp
)

val LocalAppColorScheme = staticCompositionLocalOf {
    AppColorScheme(
        primary= Color.Unspecified,
        onPrimary= Color.Unspecified,
        primaryContainer= Color.Unspecified,
        onPrimaryContainer= Color.Unspecified,
        primarySurface= Color.Unspecified,

        secondary= Color.Unspecified,
        onSecondary= Color.Unspecified,
        secondaryContainer= Color.Unspecified,
        onSecondaryContainer= Color.Unspecified,
        secondarySurface= Color.Unspecified,

        background= Color.Unspecified,
        onBackground= Color.Unspecified,

        error= Color.Unspecified,
        success= Color.Unspecified,

        warning= Color.Unspecified,
    )
}

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        headingLarge = TextStyle.Default,
        headingNormal = TextStyle.Default,
        headingSmall = TextStyle.Default,
        bodyLarge = TextStyle.Default,
        bodyNormal = TextStyle.Default,
        bodySmall = TextStyle.Default,
        bodyExtraSmall = TextStyle.Default,
        labelLarge = TextStyle.Default,
        labelNormal = TextStyle.Default,
        labelSmall = TextStyle.Default,
    )
}

val LocalAppShape = staticCompositionLocalOf {
    AppShape(
        button= RectangleShape,
        profile= RectangleShape,
        community= RectangleShape,
        image= RectangleShape
    )
}

val LocalAppSize = staticCompositionLocalOf {
    AppSize(
        extraLarge5 = Dp.Unspecified,
        extraLarge4 = Dp.Unspecified,
        extraLarge3 = Dp.Unspecified,
        extraLarge2 = Dp.Unspecified,
        extraLarge = Dp.Unspecified,
        large = Dp.Unspecified,
        medium = Dp.Unspecified,
        normal = Dp.Unspecified,
        small = Dp.Unspecified,
        extraSmall = Dp.Unspecified,
        extraSmall2 = Dp.Unspecified
    )
}
