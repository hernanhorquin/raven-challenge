package com.raven.database.di

import android.content.Context
import com.raven.database.AppDatabase
import com.raven.database.dao.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        AppDatabase.buildDatabase(context)

    @Singleton
    @Provides
    fun provideArticleDao(appDatabase: AppDatabase): ArticleDao = appDatabase.articleDao()
}