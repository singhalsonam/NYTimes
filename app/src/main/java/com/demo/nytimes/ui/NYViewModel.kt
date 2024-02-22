package com.demo.nytimes.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NYViewModel : ViewModel() {

    fun getMostViewedArticles() {
        viewModelScope.launch {
            Log.d("Response", NYRepository().getMostPopularArticles(7).toString())
        }
    }
}