package com.demo.nytimes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.nytimes.data.model.Results
import com.demo.nytimes.databinding.LayoutPopularArticlesBinding

class NYAdapter : RecyclerView.Adapter<NYAdapter.ViewHolder>() {
    var onItemClick: ((Results) -> Unit)? = null
    private var articleList = emptyList<Results>()
    inner class ViewHolder(val binding: LayoutPopularArticlesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutPopularArticlesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(articleList[position]){
                binding.tvTitle.text = this.title
                binding.tvAuthor.text= this.byline
                binding.tvPublishedDate.text= this.publishedDate
                if(this.media.isNotEmpty()) {
                    if(this.media.first()!=null){
                        val url = this.media.first()?.mediaMetadata?.first()?.url ?:""
                        Glide.with(binding.ivIcon.context)
                            .load(url)
                            .circleCrop()
                            .into(binding.ivIcon)
                    }
                }
                itemView.setOnClickListener {
                    onItemClick?.invoke(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    fun setUpArticleList(articleList: List<Results>) {
        this.articleList = articleList
        notifyDataSetChanged()
    }
}
