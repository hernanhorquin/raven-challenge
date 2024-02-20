package com.raven.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raven.home.domain.usecases.GetNewsUseCase
import com.raven.home.presentation.view.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState>
        get() = _uiState

    init {
        fetchNews()
    }

    fun fetchNews(periodSelected: Int = DEFAULT_PERIOD) {
        viewModelScope.launch {
            runCatching {
                getNewsUseCase.execute(periodSelected)
            }
                .onSuccess {
                    it.collectLatest { articles ->
                        _uiState.update { uiState.value.copy(articles = articles) }
                    }
                }
                .onFailure { _ ->
                    _uiState.update { uiState.value.copy(showError = true) }
                }
        }
    }

    fun updatePeriodSelected(period: Int) {
        _uiState.update { uiState.value.copy(articles = emptyList(), periodSelected = period) }
        fetchNews(periodSelected = period)
    }

    fun hideErrorDialog() {
        _uiState.update { uiState.value.copy(showError = false) }
        fetchNews()
    }

    companion object {
        private const val DEFAULT_PERIOD = 1
    }
}
