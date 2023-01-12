package com.faris.newsapp.services

import com.faris.newsapp.api.ArticlesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticlesStore @Inject constructor(
    private val networkRequestManager: NetworkRequestManager,
    private val articlesApi: ArticlesApi
) {
    suspend fun getMostPopular()  = networkRequestManager.apiRequest {
        articlesApi.getMostPopular()
    }
}