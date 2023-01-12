package com.faris.newsapp.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class SearchResponse (
    val docs: List<SearchArticle>
)

data class SearchArticle (
    val snippet: String,
    @SerializedName("pub_date")
    val publishedDate: Date
)