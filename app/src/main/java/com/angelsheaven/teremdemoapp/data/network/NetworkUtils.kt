package com.angelsheaven.teremdemoapp.data.network


class NetworkUtils{
    companion object {
        fun getNewsService(): NetworkService? {
            return RetrofitClient.getClient(BASE_URL)?.create(NetworkService::class.java)
        }
    }
}