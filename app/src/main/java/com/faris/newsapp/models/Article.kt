package com.faris.newsapp.models

import com.google.gson.annotations.SerializedName

data class Article(
    val id: Long,
    val url: String,
    val title: String,
    @SerializedName("published_date")
    val publishedDate: String
)