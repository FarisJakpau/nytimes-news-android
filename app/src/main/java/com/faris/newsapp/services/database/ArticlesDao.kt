package com.faris.newsapp.services.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faris.newsapp.models.Article
import com.faris.newsapp.models.PopularMenu

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(transactions: List<Article>): List<Long>

    @Query("SELECT * FROM `Article` WHERE articleMenu = :menu")
    suspend fun getArticles(menu: PopularMenu): List<Article>
}