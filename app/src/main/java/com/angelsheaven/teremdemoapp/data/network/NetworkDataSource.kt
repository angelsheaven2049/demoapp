package com.angelsheaven.teremdemoapp.data.network

import com.angelsheaven.teremdemoapp.data.storage.News
import com.angelsheaven.teremdemoapp.utilities.MyLogger
import retrofit2.Response
import javax.inject.Inject


class NetworkDataSource @Inject constructor(private val networkService: NetworkService) :MyLogger {

    suspend fun requestNews(): Response<List<Int>>? {
        return networkService?.getAllNewStoriesAsync(PRETTY_FORMAT_TYPE)?.await()
    }

    suspend fun requestNewsDetail(newsId:Int): Response<News>? {
        return networkService?.getNewsDetailAsync(newsId)?.await()
    }

}

