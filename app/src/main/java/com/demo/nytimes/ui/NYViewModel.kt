package com.demo.nytimes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.nytimes.data.model.Articles
import com.demo.nytimes.data.model.Results
import kotlinx.coroutines.launch
import retrofit2.Response

class NYViewModel : ViewModel() {
    private val nyRepository = NYRepository()
    private val resultLiveData = MutableLiveData<Results?>()
    val articleListLiveData = MutableLiveData<List<Results>?>()

    /**
    Function to fetch mostViewed articles from repository
     */
    fun fetchMostViewedArticles() {
        viewModelScope.launch {getArticleList(nyRepository.fetchMostViewedArticles())
        }

    }

    /**
     * Method to generate list of the most viewed articles in NY Times
     */
    private fun getArticleList(response: Response<Articles?>?) {
           response?.let {
               if (response.isSuccessful) {
                   response.body()?.let {
                       articleListLiveData.postValue(it.results)
                   }
               }
           }
    }

    /**
     * Method to get Article details on the basis of selected id
     *
     * @param id is the selected id
     */
    fun getArticleById(id: Double) {
        articleListLiveData.value?.let { resultsList ->
            resultLiveData.postValue(resultsList.find { it.id == id })
         //   Log.d("NYViewModel","getArticleById isSuccessful: ${resultLiveData.value}")
        }
    }

    /**
     * LiveData to return List<Results>
     */
    val articles: LiveData<List<Results>?> = articleListLiveData

    /**
     * LiveData to return Results
     */
    val articleDetail: LiveData<Results?> = resultLiveData
}