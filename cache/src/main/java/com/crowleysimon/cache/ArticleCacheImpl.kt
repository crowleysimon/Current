package com.crowleysimon.cache

import com.crowleysimon.cache.mapper.CachedArticleMapper
import com.crowleysimon.data.model.Article
import com.crowleysimon.data.repository.ArticlesCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleCacheImpl @Inject constructor(
    private val rssDatabase: RssDatabase,
    private val cachedArticleMapper: CachedArticleMapper,
) : ArticlesCache {

    override suspend fun getArticle(articleId: String): Article =
        cachedArticleMapper.mapFromCached(rssDatabase.articleDao().getArticle(articleId))

    override suspend fun insert(article: Article) =
        rssDatabase.articleDao().insert(cachedArticleMapper.mapToCached(article))

    override suspend fun insertAll(articles: List<Article>) =
        rssDatabase.articleDao().insertAll(articles.map { articleEntity ->
            cachedArticleMapper.mapToCached(articleEntity)
        })

    override fun getAllArticles(): Flow<List<Article>> {
        val articles = rssDatabase.articleDao().getAllArticles()
        return articles.map {
            it.map { cachedArticle ->
                cachedArticleMapper.mapFromCached(cachedArticle)
            }
        }
    }

    override suspend fun getArticlesForFeed(feedUrl: String): List<Article> =
        rssDatabase.articleDao().getArticlesForFeed(feedUrl)
            .map { cachedArticleMapper.mapFromCached(it) }

    override suspend fun delete(article: Article) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun update(article: Article) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}