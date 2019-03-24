package com.angelsheaven.teremdemoapp.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.angelsheaven.teremdemoapp.utilities.Converters

@Database(entities = [News::class, ReadNews::class, SavedNews::class]
        ,version = 4)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase(){

    abstract fun newsDao(): NewsDao

    abstract fun readNewsDao(): ReadNewsDao

    abstract fun savedNewsDao(): SavedNewsDao
}