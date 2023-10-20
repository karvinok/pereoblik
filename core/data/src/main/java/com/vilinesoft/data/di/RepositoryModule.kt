package com.vilinesoft.data.di

import com.vilinesoft.data.repository.MainRepositoryImpl
import com.vilinesoft.domain.repository.MainRepository
import org.koin.dsl.module

object RepositoryModule {
    fun get() = module {
        single<MainRepository> { MainRepositoryImpl(get(), get()) }
    }
}