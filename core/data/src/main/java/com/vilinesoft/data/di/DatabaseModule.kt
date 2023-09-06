package com.vilinesoft.data.di

import android.content.Context
import androidx.room.Room
import com.vilinesoft.data.db.DocumentDao
import com.vilinesoft.data.db.PereoblikDB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object DatabaseModule {

    private const val DB_NAME = "pereoblik_database"

    fun get() = module {
        single { provideDataBase(androidContext()) }
        single { provideDao(get()) }
    }

    private fun provideDataBase(context: Context): PereoblikDB {
        return Room.databaseBuilder(context, PereoblikDB::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    private fun provideDao(db: PereoblikDB): DocumentDao {
        return db.documentDao
    }

}