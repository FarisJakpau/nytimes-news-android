package com.faris.newsapp.ui.home

import androidx.lifecycle.ViewModel
import com.faris.newsapp.api.NewsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsApi: NewsApi
): ViewModel() {

}