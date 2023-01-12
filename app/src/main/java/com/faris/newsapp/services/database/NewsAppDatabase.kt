package com.faris.newsapp.services.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.faris.newsapp.models.Article
import com.faris.newsapp.utils.DatabaseConverter

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(DatabaseConverter::class)
abstract class NewsAppDatabase: RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
}