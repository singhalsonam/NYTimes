package com.demo.nytimes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.demo.nytimes.databinding.FragmentDetailBinding

class FragmentDetail : Fragment() {
    private lateinit var nyViewModel: NYViewModel
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        nyViewModel = ViewModelProvider(requireActivity())[NYViewModel::class.java]
        observeArticleDetail()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun observeArticleDetail() {
        nyViewModel.articleDetail.observe(viewLifecycleOwner, Observer {
            Log.d("FragmentDetail", "articleDetail: $it")
            binding.apply {
                it?.let {
                    tvTitle.text = it.title
                    tvDetail.text = it.abstract
                    if (it.media.isNotEmpty()) {
                        if (it.media.first() != null) {
                            val url = it.media.first()?.mediaMetadata?.first()?.url ?: ""
                            Glide.with(binding.ivImage.context)
                                .load(url)
                                .circleCrop()
                                .into(binding.ivImage)
                        }
                    }
                }
            }
        })
    }

}