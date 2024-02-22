package com.demo.nytimes.data.source.remote

import com.demo.nytimes.data.model.Articles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NYApi {
    @GET("mostviewed/all-sections/{period}.json")
    suspend fun getMostViewedArticle(
        @Path("period") period: String,
        @Query("api-key") appId: String,
    ): Response<Articles?>
}