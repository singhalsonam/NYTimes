package com.demo.nytimes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.demo.nytimes.R
import com.demo.nytimes.databinding.ActivityMainBinding
import com.demo.nytimes.utils.NetworkUtils

/**
 * Activity to display NYTimes most popular articles
 */
class NYActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var nyViewModel: NYViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nyViewModel = ViewModelProvider(this)[NYViewModel::class.java]
        binding.progressBar.visibility = View.VISIBLE
        if (NetworkUtils().isOnline(this)) {
            binding.btnRetry.visibility = View.GONE
            fetchData()
            supportFragmentManager.beginTransaction().apply {
                replace(binding.fragmentContainer.id, FragmentMostPopular())
                addToBackStack(null)
                commit()
            }
        } else {
            binding.btnRetry.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    fetchData()
                }
            }
            Toast.makeText(
                this,
                getString(R.string.something_went_wrong_please_retry), Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Method to fetch the required data
     */
    private fun fetchData() {
        nyViewModel.fetchMostViewedArticles()
    }

    /**
     * Hide progressbar
     */
    fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
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