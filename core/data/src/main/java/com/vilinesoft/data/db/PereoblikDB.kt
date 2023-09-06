package com.vilinesoft.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vilinesoft.data.model.DocumentEntity
import com.vilinesoft.data.model.DocumentItemEntity

@Database(entities = [
    DocumentEntity::class,
    DocumentItemEntity::class
], version = 1, exportSchema = false)
abstract class PereoblikDB : RoomDatabase() {
    abstract val documentDao: DocumentDao
}