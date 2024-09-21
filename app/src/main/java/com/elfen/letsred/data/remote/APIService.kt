package com.elfen.letsred.data.remote

import com.elfen.letsred.data.remote.models.RemoteAccessToken
import com.elfen.letsred.data.remote.models.RemoteDataResponse
import com.elfen.letsred.data.remote.models.RemotePage
import com.elfen.letsred.data.remote.models.RemotePost
import com.elfen.letsred.data.remote.models.RemoteUser
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("/api/v1/me")
    suspend fun getCurrentUser(): RemoteUser

    @GET("/user/{username}/about")
    suspend fun getUserByUsername(@Path("username") username: String): RemoteDataResponse<RemoteUser>

    @GET("/{sort}?sr_detail=1")
    suspend fun getListingBySort(@Path("sort") sort: String, @Query("after") after: String?):
            RemoteDataResponse<RemotePage<RemoteDataResponse<RemotePost>>>
}