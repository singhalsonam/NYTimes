package com.demo.nytimes.ui

import android.util.Log
import com.demo.nytimes.data.model.Articles
import com.demo.nytimes.data.source.remote.NYApi
import com.demo.nytimes.data.source.remote.RetrofitInstance
import com.demo.nytimes.utils.Constants
import retrofit2.Response

class NYRepository {
    /**
     * Method to get weather forecast from api
     */
    suspend fun getMostPopularArticles(period: Int) : Response<Articles?> {
        Log.d("getMostPopularArticles","Value: $period")
        return RetrofitInstance.getInstance().create(NYApi::class.java).getMostViewedArticle(
            "$period",
            Constants.APP_ID)

    }
}