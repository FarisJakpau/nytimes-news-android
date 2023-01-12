package com.faris.newsapp.ui.articles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faris.newsapp.models.AppError
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

    private val _errorFlow = MutableSharedFlow<AppError>()
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

            when(val result = articlesStore.searchArticles(query)) {
                is Result.Failure -> {
                    val error = result.error as AppError
                    _errorFlow.emit(error)
                }
                is Result.Success -> {
                    val data = result.value.response.docs
                    val tempArticles: ArrayList<Article> = arrayListOf()
                    data.forEach { searchedArticle ->
                        tempArticles.add(Article(
                            title = searchedArticle.snippet,
                            publishedDate = searchedArticle.publishedDate.toString()
                        ))
                    }
                    _articlesFlow.emit(tempArticles)
                }
            }

            _isLoadingFlow.emit(false)
        }
    }

}