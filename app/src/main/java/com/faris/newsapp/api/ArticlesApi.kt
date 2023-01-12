package com.faris.newsapp.api

import com.faris.newsapp.models.Article
import com.faris.newsapp.models.BaseResponse
import com.faris.newsapp.models.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesApi {
    @GET("mostpopular/v2/viewed/7.json?api-key=Td9ImahThDlNibEg6VJMhirBKVG2XxJS")
    suspend fun getMostViewed(): Response<BaseResponse<Article>>

    @GET("mostpopular/v2/shared/7.json?api-key=Td9ImahThDlNibEg6VJMhirBKVG2XxJS")
    suspend fun getMostShared(): Response<BaseResponse<Article>>

    @GET("mostpopular/v2/emailed/7.json?api-key=Td9ImahThDlNibEg6VJMhirBKVG2XxJS")
    suspend fun getMostEmailed(): Response<BaseResponse<Article>>

    @GET("search/v2/articlesearch.json?&api-key=Td9ImahThDlNibEg6VJMhirBKVG2XxJS")
    suspend fun searchArticles(
        @Query("q") query: String
    ): Response<BaseResponse<SearchResponse>>
}