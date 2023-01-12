package com.faris.newsapp.ui.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faris.newsapp.models.Article
import com.faris.newsapp.models.PopularMenu
import com.faris.newsapp.models.Result
import com.faris.newsapp.services.ArticlesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articlesStore: ArticlesStore
) : ViewModel(){

    private val _articlesFlow = MutableStateFlow<List<Article>>(mutableListOf())
    var articleFlow = _articlesFlow.asStateFlow()

    private val _errorFlow = MutableSharedFlow<Throwable>()
    var errorFlow = _errorFlow.asSharedFlow()

    private val _isLoadingFlow = MutableStateFlow(true)
    var isLoadingFlow = _isLoadingFlow.asStateFlow()

    fun getArticles(popularMenu: PopularMenu) {
        viewModelScope.launch {
            _isLoadingFlow.emit(true)

            when(val result = when(popularMenu) {
                PopularMenu.MostViewed -> articlesStore.getMostViewed()
                PopularMenu.MostShared -> articlesStore.getMostShared()
                PopularMenu.MostEmailed -> articlesStore.getMostEmailed()
            }) {
                is Result.Success -> {
                    _articlesFlow.emit(result.value.results)
                }
                is Result.Failure -> {
                    _errorFlow.emit(result.error)
                }
            }

            _isLoadingFlow.emit(false)
        }
    }

}