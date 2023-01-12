package com.faris.newsapp.api

import com.faris.newsapp.models.Article
import com.faris.newsapp.models.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface ArticlesApi {
    @GET("mostpopular/v2/emailed/7.json?api-key=Td9ImahThDlNibEg6VJMhirBKVG2XxJS")
    suspend fun getMostPopular(): Response<BaseResponse<Article>>
}