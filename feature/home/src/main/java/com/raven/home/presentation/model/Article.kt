package com.raven.home.presentation.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Long,
    val url: String,
    val section: String,
    val title: String,
    val publishedDate: String,
    val caption: String,
    val image: Bitmap?
): Parcelable
