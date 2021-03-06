package com.angelsheaven.teremdemoapp.data.network

import com.angelsheaven.teremdemoapp.data.storage.News
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NetworkService {

    @GET(GET_TOP_STORIES)
    fun getAllNewStoriesAsync(@Query(PRINT_FORMAT) printFormat:String)
            : Deferred<Response<List<Int>>>

    @GET(GET_NEWS_DETAIL)
    fun getNewsDetailAsync(@Path(NEWS_ID) itemId:Int): Deferred<Response<News>>
}