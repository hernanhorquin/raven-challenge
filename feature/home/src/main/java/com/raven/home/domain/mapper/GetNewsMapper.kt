package com.raven.home.domain.mapper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.raven.database.entity.ArticleDB
import com.raven.home.domain.model.ArticleEntity
import com.raven.home.presentation.model.Article
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.net.URL
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNewsMapper @Inject constructor() {

    suspend fun transformDomainToUI(params: List<ArticleEntity>): List<Article>  {
        val articlesList = mutableListOf<Article>()
        params.forEach {
            if (it.media.isNotEmpty() && it.media[0].metadata.size == 3) {
                val bytearray = imageUrlToByteArray(it.media[0].metadata[2].url)

                articlesList.add(
                    Article(
                        id = it.id,
                        url = it.url,
                        section = it.section,
                        title = it.title,
                        publishedDate = it.publishedDate,
                        caption = it.media[0].caption,
                        image = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.size)
                    )
                )
            }
        }
        return articlesList
    }

    suspend fun transformDomainToLocal(params: List<Article>): List<ArticleDB> {
        return params.map {
            ArticleDB(
                id = it.id,
                publishedDate = it.publishedDate,
                section = it.section,
                url = it.url,
                title = it.title,
                caption = it.caption,
                images = bitmapToByteArray(it.image)
            )
        }
    }

    fun transformLocalToDomain(params: List<ArticleDB>): List<Article> {
        return params.map {
            Article(
                id = it.id,
                publishedDate = it.publishedDate,
                section = it.section,
                url = it.url,
                title = it.title,
                caption = it.caption,
                image = BitmapFactory.decodeByteArray(it.images, 0, it.images.size)
            )
        }
    }

    private suspend fun imageUrlToByteArray(imageUrl: String): ByteArray {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)

                val inputStream = BufferedInputStream(url.openStream())
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()

                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 75, outputStream)
                outputStream.close()

                return@withContext outputStream.toByteArray()
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext ByteArray(0)
            }
        }
    }

    private suspend fun bitmapToByteArray(bitmap: Bitmap?): ByteArray {
        return withContext(Dispatchers.IO) {
            try {
                val outputStream = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG, 75, outputStream)
                outputStream.close()

                return@withContext outputStream.toByteArray()
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext ByteArray(0)
            }
        }
    }
}
