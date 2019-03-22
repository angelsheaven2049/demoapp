package com.angelsheaven.teremdemoapp.data.network

import com.angelsheaven.teremdemoapp.data.database.News
import retrofit2.Response

open class NetworkDataSource {

    private val service: NewsService?
            by lazy { NetworkUtils.getNewsService() }


    //Static variables
    companion object {

        private var sInstance: NetworkDataSource? = null

        fun getInstance(): NetworkDataSource? {
            return sInstance?: synchronized(this){
                NetworkDataSource().also {
                    sInstance = it
                }
            }
        }
    }


    suspend fun requestNews(): Response<List<Int>>? {
        return service?.getAllNewStoriesV2?.await()
    }

    suspend fun requestNewsDetail(newsId:Int): Response<News>? {
        return service?.getNewsDetailV2("$newsId.json")?.await()
    }

}

open class SingletonHolder<out T, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}
