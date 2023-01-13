package com.faris.newsapp

import com.faris.newsapp.api.ArticlesApi
import com.faris.newsapp.models.*
import com.faris.newsapp.services.ArticlesStore
import com.faris.newsapp.services.NetworkRequestManager
import com.faris.newsapp.services.database.ArticlesDao
import com.faris.newsapp.ui.articles.ArticlesViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class ArticlesViewModelTest {

    lateinit var articlesStore: ArticlesStore
    private var articlesDao: ArticlesDao = mock()
    private var articlesApi: ArticlesApi = mock()
    private var networkRequestManager: NetworkRequestManager = mock()
    private lateinit var viewModel: ArticlesViewModel

    private val waiter = CountDownLatch(1)
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        articlesStore = ArticlesStore(networkRequestManager, articlesApi)

        viewModel = ArticlesViewModel(articlesStore, articlesDao)
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `getMostSharedArticleSuccess`() = runBlocking {
        val mockResult = arrayListOf(
            Article(
                id = 1111,
                title = "Testing ",
                publishedDate = "20/22/2022",
                articleMenu = PopularMenu.MostShared
            )
        )
        val testResult = Result.Success(
            BaseResponse(
            results = mockResult,
            num_results = 1,
            response = Article(
                id = 1111,
                title = "Testing ",
                publishedDate = "20/22/2022",
                articleMenu = PopularMenu.MostShared
            ),
            status = "OK"
        )
        )

        whenever(articlesStore.getMostShared()).thenReturn(testResult)

        viewModel.getArticles(PopularMenu.MostShared)

        Assert.assertEquals(mockResult.size, viewModel.articleFlow.value.size)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun callAwait(millisecond: Long) {
        waiter.await(millisecond, TimeUnit.MILLISECONDS)
    }
}