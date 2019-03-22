package com.angelsheaven.teremdemoapp.utilities

import android.util.Log

interface MyLogger{
    fun log(message:String){
        val className = this.javaClass.simpleName
        Log.d(className,message)
    }
}
