package com.faris.newsapp.ui.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.faris.newsapp.databinding.ItemArticleBinding
import com.faris.newsapp.models.SearchArticle

class SearchArticlesAdapter: PagingDataAdapter<SearchArticle, SearchArticlesAdapter.ViewHolder>(SearchArticleDiffCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class SearchArticleDiffCallBack: DiffUtil.ItemCallback<SearchArticle>() {
        override fun areItemsTheSame(oldItem: SearchArticle, newItem: SearchArticle): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SearchArticle, newItem: SearchArticle): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(private val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: SearchArticle) {
            with(binding) {
                titleTextView.text = article.snippet
                dateTextView.text = article.publishedDate.toString()
            }
        }
    }
}