package com.angelsheaven.teremdemoapp.data.network

import com.angelsheaven.teremdemoapp.data.storage.News
import com.angelsheaven.teremdemoapp.utilities.MyLogger
import retrofit2.Response

open class NetworkDataSource:MyLogger {

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
        return service?.getAllNewStoriesAsync(PRETTY_FORMAT_TYPE)?.await()
    }

    suspend fun requestNewsDetail(newsId:Int): Response<News>? {
        return service?.getNewsDetailAsync(newsId)?.await()
    }

}

