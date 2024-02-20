package com.raven.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleDB(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Long,
    @ColumnInfo("url")
    val url: String,
    @ColumnInfo("section")
    val section: String,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("published_date")
    val publishedDate: String,
//    @Embedded(prefix = "media_")
//    val media: ArticleMediaDB,
    @ColumnInfo("caption")
    val caption: String,
    @ColumnInfo("images", typeAffinity = ColumnInfo.BLOB)
    val images: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ArticleDB

        if (id != other.id) return false
        if (url != other.url) return false
        if (section != other.section) return false
        if (title != other.title) return false
        if (publishedDate != other.publishedDate) return false
        if (caption != other.caption) return false
        if (!images.contentEquals(other.images)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + section.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + publishedDate.hashCode()
        result = 31 * result + caption.hashCode()
        result = 31 * result + images.contentHashCode()
        return result
    }
}

data class ArticleMediaDB(
    @ColumnInfo("caption")
    val caption: String,
    @ColumnInfo("images", typeAffinity = ColumnInfo.BLOB)
    val images: ByteArray,
    @ColumnInfo("url")
    val url: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ArticleMediaDB

        if (caption != other.caption) return false
        if (!images.contentEquals(other.images)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = caption.hashCode()
        result = 31 * result + images.contentHashCode()
        return result
    }
}