package com.raven.home.domain.usecases

import com.raven.core.bases.BaseUseCase
import com.raven.home.domain.HomeDataSource
import com.raven.home.domain.mapper.GetNewsMapper
import com.raven.home.presentation.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class GetNewsUseCase @Inject constructor(
    private val dataSource: HomeDataSource,
    private val mapper: GetNewsMapper
) : BaseUseCase<Int, List<Article>>() {

    override suspend fun execute(params: Int): Flow<List<Article>> = try {
        val articles = dataSource.getNews(params).map {
            mapper.transformDomainToUI(it)
        }
        withContext(Dispatchers.IO) {
            articles.collect {
                dataSource.saveNews(mapper.transformDomainToLocal(it))
            }
        }
        articles
    } catch (throwable: Throwable) {
        try {
            val articlesDB = mapper.transformLocalToDomain(dataSource.getNewsFromDB())
            if (articlesDB.isNotEmpty()) {
                flowOf(articlesDB)
            } else {
                throw throwable
            }
        } catch (throwable: Throwable) {
            throw throwable
        }
    }
}
