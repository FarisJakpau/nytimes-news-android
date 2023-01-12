package com.faris.newsapp.ui.articles

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.faris.newsapp.R
import com.faris.newsapp.databinding.FragmentArticlesBinding
import com.faris.newsapp.models.PopularMenu
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
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

    private val searchAdapter: SearchArticlesAdapter by lazy {
        SearchArticlesAdapter()
    }

    private val articlesType: PopularMenu?
        get() {
            val data = arguments?.get("articlesType")
            if (data != null) {
                return data as PopularMenu
            }
            return null
        }

    private val searchInput: String?
        get() = arguments?.getString("searchInput")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticlesBinding.bind(view)

        with(binding) {
            articlesType?.let {
                recyclerView.adapter = adapter
                viewModel.getArticles(it)
            }

            searchInput?.let {
                recyclerView.adapter = searchAdapter
                viewModel.searchArticles(it)
            }

            viewLifecycleOwner.lifecycleScope.launch{
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.articleFlow.collect{
                            adapter.articles = it
                        }
                    }

                    launch {
                        viewModel.isLoadingFlow.collect{
                            progressBar.isVisible = it
                        }
                    }

                    launch {
                        viewModel.errorFlow.collect{
                            Toast.makeText(this@ArticlesFragment.context, it.code.name, Toast.LENGTH_SHORT).show()
                        }
                    }

                    launch {
                        viewModel.searchResult.collect{
                            searchAdapter.submitData(it)
                        }
                    }

                    searchAdapter.loadStateFlow.collectLatest { loadState ->
                        if (loadState.refresh is LoadState.Loading) {
                            progressBar.isVisible = true
                        }
                        else{
                            progressBar.isVisible = false
                            if (loadState.append is LoadState.NotLoading && loadState.append.endOfPaginationReached) {
                                recyclerView.isVisible = searchAdapter.itemCount > 0
                                errorTextView.isVisible = searchAdapter.itemCount < 1
                                errorTextView.text = context?.getString(R.string.search_not_found)
                            }
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