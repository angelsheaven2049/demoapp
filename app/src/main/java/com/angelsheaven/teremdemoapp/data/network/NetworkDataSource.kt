package com.angelsheaven.teremdemoapp.data.network

import com.angelsheaven.teremdemoapp.data.database.News
import retrofit2.Response

class NetworkDataSource {

    private val service: NewsService?
            by lazy { NetworkUtils.getNewsService() }

    //Static variables
    companion object {

        private var sInstance: NetworkDataSource? = null
        /**
         * Get the singleton for this class
         */
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
