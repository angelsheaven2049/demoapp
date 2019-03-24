package com.angelsheaven.teremdemoapp.data.network

import com.angelsheaven.teremdemoapp.data.storage.News
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NewsService {

    @GET("topstories.json")
    fun getAllNewStoriesAsync(@Query(PRINT_FORMAT) printFormat:String)
            : Deferred<Response<List<Int>>>

    @GET("item/{id}")
    fun getNewsDetailAsync(@Path("id") itemId:String): Deferred<Response<News>>
}