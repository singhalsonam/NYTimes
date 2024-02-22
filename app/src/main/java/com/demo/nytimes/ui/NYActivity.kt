package com.demo.nytimes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.demo.nytimes.R
import androidx.activity.viewModels

class NYActivity : AppCompatActivity() {
    private val forecastViewModel: NYViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        forecastViewModel.getMostViewedArticles()
    }
}