package com.elfen.letsred.data.remote

import androidx.compose.ui.util.fastAny
import com.elfen.letsred.data.remote.models.RemoteComment
import com.elfen.letsred.data.remote.models.RemoteDataResponse
import com.elfen.letsred.data.remote.models.RemotePage
import com.elfen.letsred.data.remote.models.RemotePost
import com.elfen.letsred.data.remote.models.RemotePostComments
import com.squareup.moshi.FromJson

typealias NetworkPost = RemoteDataResponse<RemotePage<RemoteDataResponse<RemotePost>>>
typealias NetworkComments = RemoteDataResponse<RemotePage<RemoteDataResponse<RemoteComment>>>
typealias NetworkAny = RemoteDataResponse<RemotePage<RemoteDataResponse<Any>>>

class PostCommentsAdapter {
    @FromJson
    fun fromJson(data: List<NetworkAny>): RemotePostComments {
        var post: NetworkPost? = null
        var comments: NetworkComments? = null

        data.forEach {
            if(it.data.children.fastAny { child -> child.kind == "t3" }){
                post = it as NetworkPost
            }else
                comments = it as NetworkComments
        }

        return RemotePostComments(post!!, comments!!)
    }
}