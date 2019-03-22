package com.angelsheaven.teremdemoapp

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val myApplication: MyApplication){
    @Provides @Singleton fun provideContext(): Context = myApplication
}