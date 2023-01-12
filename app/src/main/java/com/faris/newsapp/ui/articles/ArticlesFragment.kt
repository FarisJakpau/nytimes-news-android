package com.faris.newsapp.ui.articles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.faris.newsapp.R
import com.faris.newsapp.databinding.FragmentArticlesBinding

class ArticlesFragment: Fragment(R.layout.fragment_articles) {

    private var _binding: FragmentArticlesBinding? = null
    private val binding: FragmentArticlesBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticlesBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}