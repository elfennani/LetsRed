package com.elfen.letsred.utilities

fun Long.readableTime(includePrecise: Boolean = false): String {
    val seconds = this / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    val months = days / 30
    val years = days / 365

    val timeUnits = mutableListOf<String>()

    if (years > 0) timeUnits.add("${years}y")
    if (months % 12 > 0) timeUnits.add("${months % 12}mo")
    if (days % 30 > 0) timeUnits.add("${days % 30}d")
    if (hours % 24 > 0) timeUnits.add("${hours % 24}h")
    if (minutes % 60 > 0) timeUnits.add("${minutes % 60}min")
    if (seconds % 60 > 0) timeUnits.add("${seconds % 60}s")

    return if (includePrecise && timeUnits.size > 1) {
        timeUnits.take(2).joinToString(" ")
    } else {
        timeUnits.firstOrNull() ?: "0s"
    }
}