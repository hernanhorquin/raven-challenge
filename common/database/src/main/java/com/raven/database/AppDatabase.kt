package com.raven.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raven.database.converter.ImagesConverter
import com.raven.database.dao.ArticleDao
import com.raven.database.entity.ArticleDB

@Database(
    version = 1,
    entities = [ArticleDB::class]
)
@TypeConverters(ImagesConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "raven-news-db")
                .fallbackToDestructiveMigration()
                .build()
    }
}