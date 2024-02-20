package com.raven.home.data.remote.model

import com.google.gson.annotations.SerializedName
import com.raven.home.domain.model.ArticleEntity

data class HomeArticlesResponse(
    val status: String,
    @SerializedName("results")
    val data: List<ArticleEntity>
)
