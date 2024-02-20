package com.raven.home.presentation.view.state

import com.raven.home.presentation.model.Article

data class HomeState(
    val articles: List<Article> = emptyList(),
    val periodSelected: Int = 1,
    val showError: Boolean = false
)
