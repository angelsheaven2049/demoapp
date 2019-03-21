package com.angelsheaven.teremdemoapp.utilities

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.ContextCompat

fun getColor(context: Context, resourceId:Int):Int{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        context.getColor(resourceId)
    }else{
        ContextCompat.getColor(context, resourceId)
    }
}

fun getDrawable(context: Context, resourceId:Int): Drawable? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        context.getDrawable(resourceId)
    } else {
        ContextCompat.getDrawable(context, resourceId)
    }
}
