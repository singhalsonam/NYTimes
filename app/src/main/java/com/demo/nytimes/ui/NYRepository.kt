package com.demo.nytimes.ui

import androidx.lifecycle.MutableLiveData
import com.demo.nytimes.data.model.Articles
import com.demo.nytimes.data.source.remote.NYApi
import com.demo.nytimes.data.source.remote.RetrofitInstance
import com.demo.nytimes.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NYRepository {

    /**
    Function to fetch Most viewed articles
     */
    suspend fun fetchMostViewedArticles(): Response<Articles?>? {
        return try {
            val response = withContext(Dispatchers.IO) {
                RetrofitInstance.getInstance().create(NYApi::class.java)
                    .getMostViewedArticle("7", Constants.APP_ID)
            }
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}