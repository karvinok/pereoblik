package com.vilinesoft.data.di

import com.vilinesoft.data.network.remote.MainApi
import org.koin.dsl.module
import retrofit2.Retrofit

object ApiModule {
    fun get() = module {
        single { provideApi(get(), MainApi::class.java) }
    }

    private fun <T> provideApi(
        retrofit: Retrofit,
        type: Class<T>
    ) = retrofit.create(type)
}