package com.elfen.letsred.data.remote

import com.elfen.letsred.data.repository.SessionRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.isDistantPast
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val sessionRepository: SessionRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (request.headers.names().contains("Authorization")) {
            return chain.proceed(request)
        }

        val userId = runBlocking { sessionRepository.activeSession.first() }
        val session = runBlocking {
            sessionRepository.sessions
                .first()
                .find { it.user.id == userId }
        } ?: return chain.proceed(request)


        var accessToken = session.accessToken
        if (session.expiration < Clock.System.now()) {
            accessToken = runBlocking { sessionRepository.refreshToken(session).accessToken }
        }

        val newRequest = request
            .newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(newRequest)
    }
}