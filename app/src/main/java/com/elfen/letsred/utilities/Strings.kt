package com.elfen.letsred.utilities

fun String.emptyAsNull(): String? {
    if(this.isEmpty())
        return null

    return this
}