package com.angelsheaven.teremdemoapp.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.angelsheaven.teremdemoapp.utilities.Converters

@Database(entities = [News::class, ReadNews::class, SavedNews::class]
        ,version = 4)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase(){

    /*companion object {

        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase?{
            return sInstance ?: synchronized(this){
                Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build().also {
                        sInstance = it
                    }
            }
        }
    }*/

    abstract fun newsDao(): NewsDao

    abstract fun readNewsDao(): ReadNewsDao

    abstract fun savedNewsDao(): SavedNewsDao
}