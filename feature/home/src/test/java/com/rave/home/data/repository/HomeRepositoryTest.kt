package com.rave.home.data.repository

import com.raven.database.AppDatabase
import com.raven.database.entity.ArticleDB
import com.raven.home.data.HomeRepository
import com.raven.home.data.remote.model.HomeArticlesResponse
import com.raven.home.data.remote.service.HomeService
import com.raven.home.domain.model.ArticleEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows

@ExperimentalCoroutinesApi
class HomeRepositoryTest {

    private lateinit var homeRepository: HomeRepository
    private lateinit var homeService: HomeService
    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        homeService = mockk(relaxed = true)
        database = mockk(relaxed = true)
        homeRepository = HomeRepository(homeService, database)
    }

    @Test
    fun `getNews should return data from service`() = runBlocking {
        // Given
        val period = 7
        val fakeNewsData = mockk<List<ArticleEntity>>()
        val articlesResponse = HomeArticlesResponse(status = "200", data = fakeNewsData)
        coEvery { (homeService.getNews(period))  } returns articlesResponse

        // Act
        val result = homeRepository.getNews(period)

        // Assert
        result.collect { news ->
            assertEquals(fakeNewsData, news)
        }
    }

    @Test(expected = Throwable::class)
    fun `getNews should throw an exception when service fails`() = runBlocking {
        // Given
        val period = 7
        val fakeError = Throwable("Fake error")
        coEvery { (homeService.getNews(period)) } throws fakeError

        // Act
        homeRepository.getNews(period)

        // Then
        coVerify { assertThrows<Throwable> { homeService.getNews(period) } }
    }

    @Test
    fun `saveNews should insert articles into the database`() = runBlocking {
        // Given
        val fakeArticles = mockk<List<ArticleDB>>(relaxed = true)

        // Act
        homeRepository.saveNews(fakeArticles)

        // Then
        fakeArticles.forEach { article ->
            // Verify that insert is called for each article
            coVerify { (database.articleDao()).insert(article) }
        }
    }

    @Test
    fun `getNewsFromDB should return articles from the database`() = runBlocking {
        // Given
        val fakeArticlesFromDB = mockk<List<ArticleDB>>()
        coEvery { (database.articleDao().current())  } returns fakeArticlesFromDB

        // Act
        val result = homeRepository.getNewsFromDB()

        // Then
        assertEquals(fakeArticlesFromDB, result)
    }
}