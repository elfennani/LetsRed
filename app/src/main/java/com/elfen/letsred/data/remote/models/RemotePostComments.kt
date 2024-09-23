package com.elfen.letsred.data.remote.models

import com.elfen.letsred.data.local.models.LocalComment
import com.elfen.letsred.data.local.models.LocalCommentMore
import com.elfen.letsred.ui.composables.postImage

data class RemotePostComments(
    val post: RemoteDataResponse<RemotePage<RemoteDataResponse<RemotePost>>>,
    val comments: RemoteDataResponse<RemotePage<RemoteDataResponse<RemoteComment>>>
)

fun RemotePage<RemoteDataResponse<RemoteComment>>.asCommentEntities(): Pair<List<LocalComment>, List<LocalCommentMore>> {
    val comments: MutableList<LocalComment> = mutableListOf()
    var moreComments: MutableList<LocalCommentMore> = mutableListOf()
    val postId = children
        .find { it.data.postFullId != null }
        ?.data
        ?.postFullId?.substring(3) ?: return Pair(emptyList(), emptyList())

    children.forEachIndexed { index, comment ->
        if (comment.kind == "more") {
            moreComments += comment.data.asMoreCommentsEntity(
                order = index.toFloat(),
                postId = postId
            )
        } else {
            comments += comment.data.asCommentEntity(index.toFloat())
        }
    }

    return Pair(comments, moreComments)
}