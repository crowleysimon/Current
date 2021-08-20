package com.crowleysimon.data.repository

import com.crowleysimon.data.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticlesCache {

    suspend fun insert(article: Article)

    suspend fun insertAll(articles: List<Article>)

    fun getAllArticles(): Flow<List<Article>>

    suspend fun getArticle(articleId: String): Article

    suspend fun getArticlesForFeed(feedUrl: String): List<Article>

    suspend fun delete(article: Article)

    suspend fun update(article: Article)
}