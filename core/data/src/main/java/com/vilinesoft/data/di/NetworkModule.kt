package com.vilinesoft.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object NetworkModule {

    const val SCHEME = "https"
    private val contentType = "application/json".toMediaType()

    fun get() = module {
        single { provideRetrofit(provideDefaultHttpClient()) }
    }

    private fun provideRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .baseUrl("${SCHEME}://localhost:8080") //todo inject some URLProvider and use it here
        .client(client)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()

    private fun provideDefaultHttpClient() = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(provideLoggingInterceptor())
        .build()

    private fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
}