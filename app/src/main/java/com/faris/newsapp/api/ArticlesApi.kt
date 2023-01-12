package com.faris.newsapp.api

import com.faris.newsapp.models.Article
import com.faris.newsapp.models.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface ArticlesApi {
    @GET("mostpopular/v2/viewed/7.json?api-key=Td9ImahThDlNibEg6VJMhirBKVG2XxJS")
    suspend fun getMostViewed(): Response<BaseResponse<Article>>

    @GET("mostpopular/v2/shared/7.json?api-key=Td9ImahThDlNibEg6VJMhirBKVG2XxJS")
    suspend fun getMostShared(): Response<BaseResponse<Article>>

    @GET("mostpopular/v2/emailed/7.json?api-key=Td9ImahThDlNibEg6VJMhirBKVG2XxJS")
    suspend fun getMostEmailed(): Response<BaseResponse<Article>>

    @GET("")
    suspend fun searchArticles(): Response<BaseResponse<Article>>
}