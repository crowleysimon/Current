package com.crowleysimon.data

import com.crowleysimon.data.model.Article
import com.crowleysimon.data.repository.ArticleRepository
import com.crowleysimon.data.repository.ArticlesCache
import com.crowleysimon.data.repository.FeedsRemote
import kotlinx.coroutines.flow.Flow

class ArticlesDataRepository(
    private val articleCache: ArticlesCache,
    private val feedRemote: FeedsRemote,
) : ArticleRepository {

    override suspend fun getArticle(articleId: String): Article? =
        articleCache.getArticle(articleId)

    override fun getAllArticles(): Flow<List<Article>> =
        articleCache.getAllArticles()

    override suspend fun fetchArticlesForFeed(feedUrl: String) {
        val articles = feedRemote.getArticlesForFeed(feedUrl).map { article ->
            val cachedArticle = articleCache.getArticle(article.guid)
            article.copy(read = cachedArticle?.read ?: article.read)
        }
        articleCache.insertAll(articles)
    }

    override suspend fun getArticlesForFeed(feedUrl: String): List<Article> =
        articleCache.getArticlesForFeed(feedUrl)

    override suspend fun markArticleRead(articleId: String) {
        val article = articleCache.getArticle(articleId)?.copy(read = true)
        return article?.let { articleCache.update(it) } ?: Unit
    }

}