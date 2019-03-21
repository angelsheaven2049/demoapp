package com.angelsheaven.teremdemoapp.data.network

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.angelsheaven.teremdemoapp.AppExecutors
import com.angelsheaven.teremdemoapp.data.database.News
import retrofit2.Response

class NetworkDataSource(private val context: Context, private val executors: AppExecutors?) {

    private val service: NewsService?
            by lazy { NetworkUtils.getNewsService() }

    //Static variables
    companion object {
        private val logTag: String = NetworkDataSource::class.java.simpleName
        @SuppressLint("StaticFieldLeak")
        private var sInstance: NetworkDataSource? = null
        private val LOCK: Any = Any()
        /**
         * Get the singleton for this class
         */
        fun getInstance(context: Context, executors: AppExecutors?): NetworkDataSource? {
            Log.d(logTag, "Getting the network data source")
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = NetworkDataSource(
                        context.applicationContext
                        , executors
                    )
                    Log.d(logTag, "Made new network data source")
                }
            }
            return sInstance
        }
    }


    suspend fun requestNews(): Response<List<Int>>? {
        return service?.getAllNewStoriesV2?.await()
    }

    suspend fun requestNewsDetail(newsId:Int): Response<News>? {
        return service?.getNewsDetailV2("$newsId.json")?.await()
    }

}
