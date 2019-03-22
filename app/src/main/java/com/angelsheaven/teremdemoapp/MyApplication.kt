package com.angelsheaven.teremdemoapp

import android.app.Application
import android.content.Context

open class MyApplication:Application(){

    lateinit var context: Context

    

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
    }

}
