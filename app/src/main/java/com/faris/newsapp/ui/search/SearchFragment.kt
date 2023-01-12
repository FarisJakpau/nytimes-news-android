package com.faris.newsapp.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.faris.newsapp.R
import com.faris.newsapp.databinding.FragmentSearchBinding
import com.faris.newsapp.models.PopularMenu

class SearchFragment: Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        with(binding) {
            searchButton.setOnClickListener {
                findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToArticlesFragment(searchInput = searchEditText.text.toString()))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}