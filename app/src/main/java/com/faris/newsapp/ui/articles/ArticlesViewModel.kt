package com.faris.newsapp.ui.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.faris.newsapp.models.*
import com.faris.newsapp.services.ArticlesStore
import com.faris.newsapp.services.database.ArticlesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articlesStore: ArticlesStore,
    private val articlesDao: ArticlesDao
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
                    val articles = result.value.results
                    articles.forEach { it.articleMenu = popularMenu }

                    articlesDao.insertOrUpdate(articles)
                    _articlesFlow.emit(articles)
                }
                is Result.Failure -> {
                    val offlineArticles = articlesDao.getArticles(popularMenu)
                    if (offlineArticles.isEmpty()) {
                        val error = result.error as AppError
                        _errorFlow.emit(error)
                    } else {
                        _articlesFlow.emit(offlineArticles)
                    }
                }
            }

            _isLoadingFlow.emit(false)
        }
    }

    fun searchArticles(query: String) {
        viewModelScope.launch {
            searchResult = articlesStore.searchArticles(query).cachedIn(viewModelScope)
        }
    }

}