package com.angelsheaven.teremdemoapp.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.angelsheaven.teremdemoapp.utilities.Converters

@Database(entities = [News::class,ReadNews::class,SavedNews::class]
        ,version = 4)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase(){
    companion object {
        private val LOG_TAG =AppDatabase::class.java.simpleName
        private const val DATABASE_NAME = "teremDemoApp"

        //For singleton instantiation
        private val LOCK:Any = Any()
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context):AppDatabase?{
            Log.d(LOG_TAG,"Getting the databases")
            if(sInstance == null){
                synchronized(LOCK){
                    sInstance = Room.databaseBuilder(context.applicationContext
                    ,AppDatabase::class.java
                    ,AppDatabase.DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build()
                }

            }

            return sInstance
        }
    }

    abstract fun newsDao():NewsDao

    abstract fun readNewsDao():ReadNewsDao

    abstract fun savedNewsDao():SavedNewsDao
}