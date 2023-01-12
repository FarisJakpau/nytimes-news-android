package com.faris.newsapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.faris.newsapp.R
import com.faris.newsapp.databinding.FragmentHomeBinding
import com.faris.newsapp.models.PopularMenu
import com.faris.newsapp.models.SearchMenu

class HomeFragment: Fragment(R.layout.fragment_home), HomeMenuAdapter.Listener{

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get()= _binding!!

    private val popularAdapter: HomeMenuAdapter<PopularMenu> by lazy {
        HomeMenuAdapter(this)
    }

    private val searchAdapter: HomeMenuAdapter<SearchMenu> by lazy {
        HomeMenuAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        with(binding) {
            popularAdapter.items = PopularMenu.values().toList()
            searchAdapter.items = SearchMenu.values().toList()

            popularRecyclerView.adapter = popularAdapter
            searchRecyclerView.adapter = searchAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun menuSelected(menu: Any) {
        when(menu) {
            is PopularMenu -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToArticlesFragment(
                    articlesType =  menu,
                ))
            }
            is SearchMenu -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
            }
        }
    }
}