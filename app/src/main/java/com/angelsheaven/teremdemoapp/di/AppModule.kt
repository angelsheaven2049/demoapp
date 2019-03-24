package com.angelsheaven.teremdemoapp.di

import android.content.Context
import com.angelsheaven.teremdemoapp.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val myApplication: MyApplication){
    @Provides @Singleton fun provideContext(): Context = myApplication


}