package com.angelsheaven.teremdemoapp.data.network

import com.angelsheaven.teremdemoapp.data.database.News
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface NewsService {
    @get:GET("topstories.json?print=pretty")
    val getAllNewStories: Call<List<Int>>

    @GET("item/{id}")
    fun getNewsDetail(@Path("id") itemId:String): Call<News>

    @get:GET("topstories.json?print=pretty")
    val getAllNewStoriesV2: Deferred<Response<List<Int>>>

    @GET("item/{id}")
    fun getNewsDetailV2(@Path("id") itemId:String): Deferred<Response<News>>
}