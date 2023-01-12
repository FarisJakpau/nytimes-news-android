package com.faris.newsapp.ui.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faris.newsapp.databinding.ItemArticleBinding
import com.faris.newsapp.models.Article

class ArticlesAdapter: RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    var articles: List<Article> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ViewHolder(private val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {
                titleTextView.text = article.title
                dateTextView.text = article.publishedDate
            }
        }
    }
}