package com.elfen.letsred.utilities

import android.text.Html

fun String.decodeEntities() = Html.fromHtml(
    this,
    Html.FROM_HTML_MODE_COMPACT
).toString()