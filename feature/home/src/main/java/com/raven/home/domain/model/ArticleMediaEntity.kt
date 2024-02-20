package com.raven.home.domain.model

import com.google.gson.annotations.SerializedName

data class ArticleMediaEntity(
    val caption: String,
    @SerializedName("media-metadata")
    val metadata: List<MediaMetadataEntity>
)

data class MediaMetadataEntity(
    val url: String
)
