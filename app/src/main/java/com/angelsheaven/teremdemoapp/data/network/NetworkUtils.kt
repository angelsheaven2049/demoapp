package com.angelsheaven.teremdemoapp.data.network

import com.angelsheaven.teremdemoapp.utilities.BASE_URL

class NetworkUtils{
    companion object {
        fun getNewsService(): NewsService? {
            return RetrofitClient.getClient(BASE_URL)?.create(NewsService::class.java)
        }
    }
}