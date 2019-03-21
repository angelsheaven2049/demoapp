package com.angelsheaven.teremdemoapp

import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Global executor pools for the whole application
 * Grouping tasks like this avoids the effects of task starvation (e.g disk reads don't wait behind
 * webservice request)
 */
class AppExecutors(_diskIO:Executor,_networkIO:Executor,_mainThread:Executor){
    //For Singleton instantiation
    companion object {
        private val LOCK:Any = Any()
        private var sInstance: AppExecutors? = null
        fun getInstance(): AppExecutors? {
            if(sInstance ==null){
                synchronized(LOCK){
                    sInstance = AppExecutors(Executors.newSingleThreadExecutor()
                            , Executors.newFixedThreadPool(3)
                            , MainThreadExecutor())
                }
            }
            return sInstance
        }

        private class MainThreadExecutor: Executor{
            private val mainThreadHandler:Handler = Handler(Looper.getMainLooper())

            override fun execute(@NonNull command:Runnable){
                mainThreadHandler.post(command)
            }
        }

    }

    private val diskIO:Executor = _diskIO
    private val mainThread:Executor = _mainThread
    private val networkIO:Executor = _networkIO

    fun diskIO():Executor{
        return diskIO
    }

    fun mainThread():Executor{
        return mainThread
    }

    fun networkIO():Executor{
        return networkIO
    }


}