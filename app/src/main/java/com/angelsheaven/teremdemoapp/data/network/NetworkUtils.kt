package com.angelsheaven.teremdemoapp.data.network


class NetworkUtils{
    companion object {
        fun getNewsService(): NewsService? {
            return RetrofitClient.getClient(BASE_URL)?.create(NewsService::class.java)
        }
    }
}