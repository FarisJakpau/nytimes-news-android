package com.faris.newsapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faris.newsapp.databinding.ItemMenuBinding
import com.faris.newsapp.models.PopularMenu
import com.faris.newsapp.models.SearchMenu


class HomeMenuAdapter<T>(
    private val listener: Listener
): RecyclerView.Adapter<HomeMenuAdapter<T>.ViewHolder<T>>() {

    var items: List<T> = mutableListOf()

    interface Listener {
        fun menuSelected(menu: Any)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder<T>(private val binding:ItemMenuBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: T) {
            with(binding) {
                root.setOnClickListener {
                    listener.menuSelected(menu as Any)
                }

                when(menu) {
                    is PopularMenu -> {
                        menuTextView.text = root.context.getString(menu.nameRes)
                    }

                    is SearchMenu -> {
                        menuTextView.text = root.context.getString(menu.nameRes)
                    }
                }
            }
        }
    }
}