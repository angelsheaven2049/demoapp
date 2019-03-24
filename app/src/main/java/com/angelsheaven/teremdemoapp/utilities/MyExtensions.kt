package com.angelsheaven.teremdemoapp.utilities

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.androidadvance.topsnackbar.TSnackbar
import com.angelsheaven.teremdemoapp.INITIALIZE_DATA
import com.google.android.material.snackbar.Snackbar

//Livedata extension function
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
    observeForever(object: Observer<T> {
        override fun onChanged(t:T?){
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

//Activity extension functions
fun Activity.isMyDataItInitialized():Boolean {
    val shareRef = this.getPreferences(Context.MODE_PRIVATE) ?: return false

    return shareRef.getBoolean(INITIALIZE_DATA,false)
}

fun Activity.getMyPreferences(): SharedPreferences?{
    return this.getPreferences(Context.MODE_PRIVATE) ?: null
}

//Context extension functions
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

fun Context.isMyNetworkConnected():Boolean{
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}

//View extension function
fun View.mySnackBar(message: Int, backgroundColor: Int): TSnackbar {
    return TSnackbar.make(this, message, Snackbar.LENGTH_LONG).also {
        val snackBarView = it.view

        //Set textview aligns center
        val snackBarTextView = snackBarView
            .findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        snackBarTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER

        //Change background color of snackbar
        val color =
            context.myGetColor(backgroundColor)

        it.view.setBackgroundColor(color)

    }
}

fun View.mySnackBar(message: String, backgroundColor: Int): TSnackbar {
    return TSnackbar.make(this, message, Snackbar.LENGTH_LONG).also {
        val snackBarView = it.view

        //Set textview aligns center
        val snackBarTextView = snackBarView
            .findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        snackBarTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER

        //Change background color of snackbar
        val color =
            context.myGetColor(backgroundColor)

        it.view.setBackgroundColor(color)

    }
}