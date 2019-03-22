package com.angelsheaven.teremdemoapp.utilities

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.ContextCompat

fun Context.myGetDrawable(context: Context, resourceId:Int): Drawable? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        context.getDrawable(resourceId)
    } else {
        ContextCompat.getDrawable(context, resourceId)
    }
}

fun Context.myGetColor(resourceId:Int):Int{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.getColor(resourceId)
    }else{
        ContextCompat.getColor(this, resourceId)
    }
}