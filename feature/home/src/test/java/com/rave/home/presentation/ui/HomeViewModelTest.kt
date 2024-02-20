package com.rave.home.presentation.ui

import app.cash.turbine.test
import com.raven.home.domain.usecases.GetNewsUseCase
import com.raven.home.presentation.model.Article
import com.raven.home.presentation.viewmodel.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var getNewsUseCase: GetNewsUseCase
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        getNewsUseCase = mockk(relaxed = true)
        viewModel = HomeViewModel(getNewsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchNews success should update UI state with articles`() = runBlocking {
        // Given
        val articles = mockk<List<Article>>(relaxed = true)
        coEvery { getNewsUseCase.execute(1) } returns flowOf(articles)

        // When
        viewModel.fetchNews(1)

        // Then
        viewModel.uiState.test {
            val actualState = expectMostRecentItem()
            assertEquals(articles, actualState.articles)
        }
    }

    @Test
    fun `fetchNews failure should update UI state with showError true`() = runBlocking {
        // Given
        coEvery { getNewsUseCase.execute(1) } throws Exception("Fake error")

        // When
        viewModel.fetchNews()

        // Then
        viewModel.uiState.test {
            val actualState = expectMostRecentItem()
            assertEquals(true, actualState.showError)
        }
    }

    @Test
    fun `updatePeriodSelected should update UI state with new period and empty articles`() = runBlocking {
        // Given
        val newPeriod = 7

        // When
        viewModel.updatePeriodSelected(newPeriod)

        // Then
        viewModel.uiState.test {
            val actualState = expectMostRecentItem()
            assertEquals(true, actualState.articles.isEmpty())
            assertEquals(7, actualState.periodSelected)
            assertEquals(false, actualState.showError)
        }
    }

    @Test
    fun `hideErrorDialog should update UI state with showError false`() = runBlocking {
        // When
        viewModel.hideErrorDialog()

        // Then
        viewModel.uiState.test {
            val actualState = expectMostRecentItem()
            assertEquals(false, actualState.showError)
        }
    }
}