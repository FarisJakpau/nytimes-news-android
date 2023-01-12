package com.faris.newsapp.ui.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.faris.newsapp.models.*
import com.faris.newsapp.services.ArticlesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articlesStore: ArticlesStore
) : ViewModel(){

    private val _articlesFlow = MutableStateFlow<List<Article>>(mutableListOf())
    var articleFlow = _articlesFlow.asStateFlow()

    private val _errorFlow = MutableSharedFlow<AppError>()
    var errorFlow = _errorFlow.asSharedFlow()

    private val _isLoadingFlow = MutableStateFlow(true)
    var isLoadingFlow = _isLoadingFlow.asStateFlow()

    var searchResult: Flow<PagingData<SearchArticle>> = flow { }

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
                    val error = result.error as AppError
                    _errorFlow.emit(error)
                }
            }

            _isLoadingFlow.emit(false)
        }
    }

    fun searchArticles(query: String) {
        viewModelScope.launch {
            _isLoadingFlow.emit(true)

            searchResult = articlesStore.searchArticles(query)

            _isLoadingFlow.emit(false)
        }
    }

}