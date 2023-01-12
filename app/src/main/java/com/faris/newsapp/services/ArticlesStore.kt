package com.faris.newsapp.services

import com.faris.newsapp.api.ArticlesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticlesStore @Inject constructor(
    private val networkRequestManager: NetworkRequestManager,
    private val articlesApi: ArticlesApi
) {
    suspend fun getMostViewed()  = networkRequestManager.apiRequest {
        articlesApi.getMostViewed()
    }

    suspend fun getMostShared()  = networkRequestManager.apiRequest {
        articlesApi.getMostShared()
    }

    suspend fun getMostEmailed()  = networkRequestManager.apiRequest {
        articlesApi.getMostEmailed()
    }

    suspend fun searchArticles(query: String) = networkRequestManager.apiRequest {
        articlesApi.searchArticles(query)
    }
}