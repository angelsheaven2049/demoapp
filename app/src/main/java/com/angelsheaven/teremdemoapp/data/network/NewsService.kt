package com.angelsheaven.teremdemoapp.data.network

import com.angelsheaven.teremdemoapp.data.News
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface NewsService {

    @get:GET("topstories.json?print=pretty")
    val getAllNewStories: Deferred<Response<List<Int>>>

    @GET("item/{id}")
    fun getNewsDetailAsync(@Path("id") itemId:String): Deferred<Response<News>>
}