package com.elfen.letsred.data.remote.models

data class RemoteDataResponse<T>(
    val kind: String,
    val data: T
)