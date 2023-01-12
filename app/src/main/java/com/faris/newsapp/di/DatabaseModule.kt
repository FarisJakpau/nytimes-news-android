package com.faris.newsapp.di

import android.content.Context
import androidx.room.Room
import com.faris.newsapp.services.database.ArticlesDao
import com.faris.newsapp.services.database.NewsAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): NewsAppDatabase {
        return Room.databaseBuilder(
            context,
            NewsAppDatabase::class.java,
            "news-app-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticlesDao(
        database: NewsAppDatabase
    ):ArticlesDao {
        return database.articlesDao()
    }
}