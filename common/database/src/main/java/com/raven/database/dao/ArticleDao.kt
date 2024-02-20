package com.raven.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raven.database.entity.ArticleDB

@Dao
abstract class ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(article: ArticleDB)

    @Query("SELECT * FROM articles")
    abstract fun current(): List<ArticleDB>

    @Query("DELETE FROM articles")
    abstract fun deleteAll()
}