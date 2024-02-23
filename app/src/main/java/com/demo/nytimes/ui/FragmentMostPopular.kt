package com.demo.nytimes.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.nytimes.databinding.FragmentMostPopularBinding

class FragmentMostPopular : Fragment() {
    private lateinit var binding: FragmentMostPopularBinding
    private lateinit var nyAdapter: NYAdapter
    private lateinit var nyViewModel: NYViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nyViewModel = ViewModelProvider(requireActivity())[NYViewModel::class.java]
        binding = FragmentMostPopularBinding.inflate(inflater,container,false)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        observeMostViewedArticles()
        binding.rvArticles.layoutManager = layoutManager
        nyAdapter = NYAdapter()
        binding.rvArticles.adapter = nyAdapter
        return binding.root
    }

    /**
     * get and populate most viewed article list
     */
    private fun observeMostViewedArticles() {
        val nyActivity = requireActivity() as NYActivity
        nyViewModel.articles.observe(viewLifecycleOwner, Observer {
            nyActivity.hideProgressBar()
            Log.d("FragmentMostPopular","ItemList: $it")
            nyAdapter.setUpArticleList(it?: emptyList())
            nyAdapter.onItemClick = { result ->
                Log.d("FragmentMostPopular",result.title?:"empty")
                result.id?.let { it1 -> nyViewModel.getArticleById(it1) }
                nyActivity.changeFragment(FragmentDetail())
            }
        })
    }
}