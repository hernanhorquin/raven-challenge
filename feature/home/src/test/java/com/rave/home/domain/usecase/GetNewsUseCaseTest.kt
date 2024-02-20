package com.rave.home.domain.usecase

import com.raven.database.entity.ArticleDB
import com.raven.home.domain.HomeDataSource
import com.raven.home.domain.mapper.GetNewsMapper
import com.raven.home.domain.model.ArticleEntity
import com.raven.home.domain.usecases.GetNewsUseCase
import com.raven.home.presentation.model.Article
import io.mockk.mockk
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows

@ExperimentalCoroutinesApi
class GetNewsUseCaseTest {

    private lateinit var useCase: GetNewsUseCase
    private lateinit var dataSource: HomeDataSource
    private lateinit var mapper: GetNewsMapper

    @Before
    fun setup() {
        dataSource = mockk(relaxed = true)
        mapper = mockk(relaxed = true)
        useCase = GetNewsUseCase(dataSource, mapper)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `use case should return articles when data source call succeeds`() = runBlocking {
        // Given
        val domainArticles = mockk<List<ArticleEntity>>(relaxed = true)
        val uiArticles = mockk<List<Article>>(relaxed = true)
        coEvery { dataSource.getNews(any()) } returns flowOf(domainArticles)
        coEvery { mapper.transformDomainToUI(any()) } returns uiArticles

        // When
        val result = useCase.execute(1)

        // Then
        result.collect { articles ->
            assert(articles == uiArticles)
        }
        coVerify { dataSource.saveNews(any()) }
    }

    @Test
    fun `use case should return articles from DB when data source call fails and DB has data`() =
        runBlocking {
            // Given
            val domainArticlesDB = mockk<List<ArticleDB>>(relaxed = true)
            val uiArticles = mockk<List<Article>>(relaxed = true)
            coEvery { dataSource.getNews(any()) } throws Exception("Fake error")
            coEvery { dataSource.getNewsFromDB() } returns domainArticlesDB
            coEvery { mapper.transformLocalToDomain(any()) } returns uiArticles

            // When
            val result = useCase.execute(1)

            // Then
            result.collect { articles ->
                assert(articles == uiArticles)
            }
            coVerify(exactly = 0) { dataSource.saveNews(any()) }
        }

    @Test(expected = Exception::class)
    fun `use case should throw exception when both data source call and DB access fail`() =
        runBlocking {
            // Given
            coEvery { dataSource.getNews(1) } throws Exception("Fake error")
            coEvery { dataSource.getNewsFromDB() } throws Exception("Fake error")

            // When
            useCase.execute(1)

            // Then
            coVerify { assertThrows<Exception> { dataSource.getNews(1) } }
            coVerify { assertThrows<Exception> { dataSource.getNewsFromDB() } }
        }
}
