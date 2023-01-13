package com.faris.newsapp.services.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.faris.newsapp.models.Article
import com.faris.newsapp.models.PopularMenu
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsAppDatabaseTest: TestCase() {
    private lateinit var database: NewsAppDatabase
    private lateinit var articlesDao: ArticlesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsAppDatabase::class.java
        ).allowMainThreadQueries().build()

        articlesDao = database.articlesDao()
    }

    @Test
    fun insert() = runBlocking {
        val article = Article(
            id = 11111,
            title = "Unit Test",
            publishedDate = "22/2/2033",
            articleMenu = PopularMenu.MostShared
        )
        val articles = arrayListOf<Article>()
        articles.add(article)

        articlesDao.insertOrUpdate(articles)

        val data = articlesDao.getArticles(PopularMenu.MostShared)
        assertThat(data.contains(article)).isTrue()
    }

    @After
    fun closeDatabase() {
        database.close()
    }
}