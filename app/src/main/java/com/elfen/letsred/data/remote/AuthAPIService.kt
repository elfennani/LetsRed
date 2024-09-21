package com.elfen.letsred.data.remote

import com.elfen.letsred.data.remote.models.RemoteAccessToken
import com.elfen.letsred.data.remote.models.RemoteUser
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthAPIService {
    @FormUrlEncoded
    @POST("https://www.reddit.com/api/v1/access_token")
    suspend fun getAccessToken(
        @Header("Authorization") authorization: String,
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("grant_type") grantType: String = "authorization_code"
    ): RemoteAccessToken

    @FormUrlEncoded
    @POST("https://www.reddit.com/api/v1/access_token")
    suspend fun getAccessTokenFromRefreshToken(
        @Header("Authorization") authorization: String,
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String = "refresh_token"
    ): RemoteAccessToken

    @GET("/api/v1/me")
    suspend fun getCurrentUser(
        /**
         * Authorization Bearer header
         */
        @Header("Authorization") authorization: String,
    ): RemoteUser
}