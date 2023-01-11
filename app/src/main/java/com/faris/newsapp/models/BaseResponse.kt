package com.faris.newsapp.models

data class BaseResponse<T> (
    val status: String,
    val num_results: Int,
    val results: List<T>
)