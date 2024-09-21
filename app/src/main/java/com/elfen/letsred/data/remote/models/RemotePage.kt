package com.elfen.letsred.data.remote.models

data class RemotePage<T>(
    val after: String?,
    val before: String?,
    val children: List<T>,
)
