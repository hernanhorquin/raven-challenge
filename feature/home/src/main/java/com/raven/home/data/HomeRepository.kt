package com.raven.home.data

import com.raven.database.AppDatabase
import com.raven.database.entity.ArticleDB
import com.raven.home.data.remote.service.HomeService
import com.raven.home.domain.HomeDataSource
import com.raven.home.domain.model.ArticleEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class HomeRepository @Inject constructor(
    private val homeService: HomeService,
    private val database: AppDatabase
) : HomeDataSource {

    override suspend fun getNews(period: Int): Flow<List<ArticleEntity>> = try {
        withContext(Dispatchers.IO) {
            flowOf(homeService.getNews(period).data)
        }
    } catch (throwable: Throwable) {
        throw throwable
    }

    override suspend fun saveNews(articles: List<ArticleDB>) {
        withContext(Dispatchers.IO) {
            database.articleDao().deleteAll()
            articles.forEach {
                database.articleDao().insert(it)
            }
        }
    }

    override suspend fun getNewsFromDB(): List<ArticleDB> =
        withContext(Dispatchers.IO) {
            database.articleDao().current()
        }
}
