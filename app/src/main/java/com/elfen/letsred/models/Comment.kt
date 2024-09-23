package com.elfen.letsred.models

sealed class Comment(open val order: Float, open val depth: Int) {
    data class Content(
        val id: String,
        val body: String,
        val authorId: String?,
        val authorUsername: String?,
        override val depth: Int,
        override val order: Float,
    ): Comment(order, depth)

    data class More(
        val id: String,
        override val depth: Int,
        override val order: Float
    ): Comment(order, depth)
}
