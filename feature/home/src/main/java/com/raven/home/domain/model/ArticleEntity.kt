package com.raven.home.domain.model

import com.google.gson.annotations.SerializedName

data class ArticleEntity(
    val id: Long,
    val url: String,
    val section: String,
    val title: String,
    @SerializedName("published_date")
    val publishedDate: String,
    val source: String,
    val media: List<ArticleMediaEntity>
)
