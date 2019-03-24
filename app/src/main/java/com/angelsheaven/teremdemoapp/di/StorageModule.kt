package com.angelsheaven.teremdemoapp.di

import android.content.Context
import androidx.room.Room
import com.angelsheaven.teremdemoapp.data.storage.AppDatabase
import com.angelsheaven.teremdemoapp.data.storage.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()


}