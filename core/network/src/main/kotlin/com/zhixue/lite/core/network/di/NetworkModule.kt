package com.zhixue.lite.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zhixue.lite.core.network.api.ChangYanApi
import com.zhixue.lite.core.network.api.GiteeApi
import com.zhixue.lite.core.network.api.ZhixueApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

private const val GITEE_BASE_URL = "https://gitee.com"
private const val ZHIXUE_BASE_URL = "https://www.zhixue.com"
private const val CHANGYAN_BASE_URL = "https://open.changyan.com"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideCallFactory(interceptor: Interceptor): Call.Factory {
        return OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGiteeApi(
        networkJson: Json,
        callFactory: Call.Factory
    ): GiteeApi {
        return Retrofit.Builder()
            .baseUrl(GITEE_BASE_URL)
            .callFactory(callFactory)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(GiteeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideZhixueApi(
        networkJson: Json,
        callFactory: Call.Factory
    ): ZhixueApi {
        return Retrofit.Builder()
            .baseUrl(ZHIXUE_BASE_URL)
            .callFactory(callFactory)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ZhixueApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChangYanApi(
        networkJson: Json,
        callFactory: Call.Factory
    ): ChangYanApi {
        return Retrofit.Builder()
            .baseUrl(CHANGYAN_BASE_URL)
            .callFactory(callFactory)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ChangYanApi::class.java)
    }
}