package com.angelsheaven.teremdemoapp

import android.app.Application
import com.angelsheaven.teremdemoapp.di.AppComponent
import com.angelsheaven.teremdemoapp.di.AppModule

open class MyApplication:Application(){



    companion object {
        lateinit var appComponent: AppComponent
    }


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

}
