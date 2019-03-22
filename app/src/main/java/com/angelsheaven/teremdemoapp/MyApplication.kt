package com.angelsheaven.teremdemoapp

import android.app.Application

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
