package com.faris.newsapp.services

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.faris.newsapp.api.ArticlesApi
import com.faris.newsapp.models.SearchArticle

class ArticlesPagingSource (
    private val articlesApi: ArticlesApi,
    private val query: String
): PagingSource<Int, SearchArticle>() {

    companion object {
        private const val ARTICLES_STARTING_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchArticle> {
        val pageIndex = params.key ?: ARTICLES_STARTING_INDEX

        return try {
            val response = articlesApi.searchArticles(
                query = query,
                page = pageIndex
            ).body()

            val searchedArticles = response?.response

            val nextKey = if(searchedArticles == null) {
                null
            } else {
                pageIndex + 1
            }

            LoadResult.Page(
                nextKey = nextKey,
                prevKey = if (pageIndex == ARTICLES_STARTING_INDEX) null else pageIndex,
                data = searchedArticles?.docs ?: mutableListOf()
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchArticle>): Int? {
        return null
    }
}