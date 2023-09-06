package com.vilinesoft.pereoblik

import android.app.Application
import com.vilinesoft.data.di.ApiModule
import com.vilinesoft.data.di.NetworkModule
import com.vilinesoft.data.di.RepositoryModule
import com.vilinesoft.navigation.di.VMModule
import com.vilinesoft.pereoblik.di.AppModule
import com.vilinesoft.data.di.DatabaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PereoblikApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(applicationContext)

            modules(listOf(
                AppModule.get(),
                NetworkModule.get(),
                ApiModule.get(),
                DatabaseModule.get(),
                RepositoryModule.get(),
                VMModule.get(),
            ))
        }
    }
}