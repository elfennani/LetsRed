package com.elfennani.letsred.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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

val Inter = FontFamily(
    inter,
    interSemibold,
)

val InterTight = FontFamily(
    interTightSemibold,
    interTightBold
)