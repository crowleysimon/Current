package com.crowleysimon.cache

import com.crowleysimon.cache.mapper.CachedArticleMapper
import com.crowleysimon.data.model.ArticleEntity
import com.crowleysimon.data.repository.ArticlesCache
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ArticleCacheImpl @Inject constructor(
    private val rssDatabase: RssDatabase,
    private val cachedArticleMapper: CachedArticleMapper
) : ArticlesCache {

    override fun getArticle(articleId: String): Observable<ArticleEntity> {
        return rssDatabase.articleDao()
            .getArticle(articleId)
            .map { cachedArticle ->
                cachedArticleMapper.mapFromCached(cachedArticle)
            }
    }

    override fun insert(article: ArticleEntity): Completable {
        return Completable.defer {
            rssDatabase.articleDao()
                .insert(cachedArticleMapper.mapToCached(article))
            Completable.complete()
        }
    }

    override fun insertAll(articles: List<ArticleEntity>): Completable {
        return Completable.defer {
            rssDatabase.articleDao()
                .insertAll(articles
                    .map { articleEntity ->
                        cachedArticleMapper.mapToCached(articleEntity)
                    })
            Completable.complete()
        }
    }

    override fun getAllArticles(): Observable<List<ArticleEntity>> {
        return rssDatabase.articleDao()
            .getAllArticles()
            .map { articles ->
                articles.map { cachedArticle ->
                    cachedArticleMapper.mapFromCached(cachedArticle)
                }
            }
    }

    override fun getArticlesForFeed(feedUrl: String): Observable<List<ArticleEntity>> {
        return rssDatabase.articleDao()
            .getArticlesForFeed(feedUrl)
            .map { articles ->
                articles.map { cachedArticle ->
                    cachedArticleMapper.mapFromCached(cachedArticle)
                }
            }
    }

    override fun delete(article: ArticleEntity): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(article: ArticleEntity): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}