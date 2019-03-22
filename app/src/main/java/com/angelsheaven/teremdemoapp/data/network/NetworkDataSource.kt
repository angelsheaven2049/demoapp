package com.angelsheaven.teremdemoapp.data.network

import com.angelsheaven.teremdemoapp.data.News
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
        return service?.getAllNewStories?.await()
    }

    suspend fun requestNewsDetail(newsId:Int): Response<News>? {
        return service?.getNewsDetailAsync("$newsId.json")?.await()
    }

}

