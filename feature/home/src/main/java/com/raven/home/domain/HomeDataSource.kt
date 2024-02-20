package com.raven.home.domain

import com.raven.database.entity.ArticleDB
import com.raven.home.domain.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {

    suspend fun getNews(period: Int): Flow<List<ArticleEntity>>

    suspend fun saveNews(articles: List<ArticleDB>)

    suspend fun getNewsFromDB(): List<ArticleDB>
}
