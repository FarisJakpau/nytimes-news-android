package com.faris.newsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Article(
    @PrimaryKey
    val id: Long? = null,
    val title: String,
    @SerializedName("published_date")
    val publishedDate: String,
    var articleMenu: PopularMenu
)