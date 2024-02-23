package com.demo.nytimes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.demo.nytimes.databinding.ActivityMainBinding

class NYActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var nyViewModel: NYViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nyViewModel = ViewModelProvider(this)[NYViewModel::class.java]
        nyViewModel.fetchMostViewedArticles()
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragmentContainer.id, FragmentMostPopular())
            addToBackStack(null)
            commit()
        }
    }

    /**
     * Method to change fragment
     */
     fun changeFragment(fragmentToChange: Fragment): Unit {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragmentContainer.id, fragmentToChange)
            addToBackStack(null)
            commit()
        }
    }
}