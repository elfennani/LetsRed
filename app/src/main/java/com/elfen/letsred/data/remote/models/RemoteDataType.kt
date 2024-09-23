package com.elfen.letsred.data.remote.models

sealed class RemoteDataType(val kind: DataType){
    data class Post(val data: RemotePost): RemoteDataType(DataType.POST)
    data class Comment(val data: RemoteComment): RemoteDataType(DataType.COMMENT)
    data class MoreComment(val data: RemoteMoreComment): RemoteDataType(DataType.MORE_COMMENTS)
}