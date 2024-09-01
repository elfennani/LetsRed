package com.elfen.letsred.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.sp
import com.elfen.letsred.R

val inter = Font(
    resId = R.font.inter,
    weight = FontWeight.Normal
)

val interSemibold = Font(
    resId = R.font.inter_semibold,
    weight = FontWeight.SemiBold
)

val interTightSemibold = Font(
    resId = R.font.inter_tight_semibold,
    weight = FontWeight.SemiBold
)

val interTightBold = Font(
    resId = R.font.inter_tight_bold,
    weight = FontWeight.Bold
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = interTightBold.toFontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 33.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = interTightBold.toFontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 27.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = interTightSemibold.toFontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
    ),


    bodyLarge = TextStyle(
        fontFamily = inter.toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = inter.toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 19.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = inter.toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.12.sp
    ),


    labelLarge = TextStyle(
        fontFamily = interSemibold.toFontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.32.sp
    ),
    labelMedium = TextStyle(
        fontFamily = interSemibold.toFontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.28.sp
    ),
    labelSmall = TextStyle(
        fontFamily = interSemibold.toFontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.24.sp
    )
)