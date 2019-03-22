package com.angelsheaven.teremdemoapp.utilities

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
    observeForever(object: Observer<T> {
        override fun onChanged(t:T?){
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}
