package com.faris.newsapp.ui.articles

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.faris.newsapp.R
import com.faris.newsapp.databinding.FragmentArticlesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticlesFragment: Fragment(R.layout.fragment_articles) {

    private var _binding: FragmentArticlesBinding? = null
    private val binding: FragmentArticlesBinding
        get() = _binding!!

    private val viewModel: ArticlesViewModel by viewModels()

    private val adapter: ArticlesAdapter by lazy {
        ArticlesAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticlesBinding.bind(view)
        viewModel.getMostPopular()
        with(binding) {
            recyclerView.adapter = adapter

            viewLifecycleOwner.lifecycleScope.launch{
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.articleFlow.collect{
                            adapter.articles = it
                        }
                    }

                    launch {
                        viewModel.isLoadingFlow.collect{
                            loadingTextView.isVisible = it
                        }
                    }

                    launch {
                        viewModel.errorFlow.collect{
                        }
                    }

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}