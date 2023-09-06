package com.vilinesoft.pereoblik.di

import com.vilinesoft.pereoblik.CacheManager
import com.vilinesoft.pereoblik.PereoblikApplication
import com.vilinesoft.ui.keyprocessing.KeyEventBus
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AppModule {
    fun get() = module {

        single { KeyEventBus() }
        single { androidApplication() as PereoblikApplication }
        single { CacheManager(androidContext()) }
    }
}