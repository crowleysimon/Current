package com.crowleysimon.data.repository

import com.crowleysimon.data.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    suspend fun getArticle(articleId: String): Article?

    fun getAllArticles(): Flow<List<Article>>

    suspend fun fetchArticlesForFeed(feedUrl: String)

    suspend fun getArticlesForFeed(feedUrl: String): List<Article>

    suspend fun markArticleRead(articleId: String)

}