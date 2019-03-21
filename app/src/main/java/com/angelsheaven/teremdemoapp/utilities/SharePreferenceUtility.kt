package com.angelsheaven.teremdemoapp.utilities

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences


fun isItInitializedData(activity: Activity?):Boolean {
    val shareRef = activity?.getPreferences(Context.MODE_PRIVATE) ?: return false

    return shareRef.getBoolean(INITIALIZE_DATA,false)
}

fun Activity.getMyPreferences():SharedPreferences?{
    return this.getPreferences(Context.MODE_PRIVATE) ?: null
}