package com.elfen.letsred.di

import com.elfen.letsred.data.remote.APIService
import com.elfen.letsred.data.remote.AuthAPIService
import com.elfen.letsred.data.remote.AuthInterceptor
import com.elfen.letsred.data.remote.models.DataType
import com.elfen.letsred.data.remote.models.RemoteComment
import com.elfen.letsred.data.remote.models.RemoteDataType
import com.elfen.letsred.data.remote.models.RemoteMoreComment
import com.elfen.letsred.data.remote.models.RemotePost
import com.elfen.letsred.data.repository.SessionRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    private val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    fun provideBaseUrl(): String = "https://oauth.reddit.com/"

    @Provides
    @Singleton
    fun provideAuthInterceptor(sessionRepository: SessionRepository): AuthInterceptor {
        return AuthInterceptor(sessionRepository)
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .add(
                PolymorphicJsonAdapterFactory.of(RemoteDataType::class.java, "kind")
                    .withSubtype(RemoteDataType.Post::class.java, DataType.POST.value)
                    .withSubtype(RemoteDataType.Comment::class.java, DataType.COMMENT.value)
                    .withSubtype(RemoteDataType.MoreComment::class.java, DataType.MORE_COMMENTS.value)
            )
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit, authInterceptor: AuthInterceptor): APIService =
        retrofit
            .newBuilder()
            .client(okHttpClient.newBuilder().addInterceptor(authInterceptor).build())
            .build()
            .create(APIService::class.java)

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit) = retrofit.create(AuthAPIService::class.java)!!
}