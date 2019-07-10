package com.crowleysimon.data

import com.crowleysimon.data.mapper.ArticleMapper
import com.crowleysimon.data.repository.ArticlesCache
import com.crowleysimon.data.repository.FeedsRemote
import com.crowleysimon.domain.model.Article
import com.crowleysimon.domain.repository.ArticleRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ArticlesDataRepository @Inject constructor(
    private val articleMapper: ArticleMapper,
    private val articleCache: ArticlesCache,
    private val feedRemote: FeedsRemote
) : ArticleRepository {

    override fun getArticle(articleId: String): Observable<Article> {
        return articleCache.getArticle(articleId)
            .map { articleEntity ->
                articleMapper.mapFromEntity(articleEntity)
            }
    }

    override fun getAllArticles(): Observable<List<Article>> {
        return articleCache.getAllArticles()
            .map { articleEntities ->
                articleEntities.map { entity -> articleMapper.mapFromEntity(entity) }
            }
    }

    override fun fetchArticlesForFeed(feedUrl: String): Completable {
        return feedRemote.getArticlesForFeed(feedUrl)
            .flatMapCompletable { articleEntities ->
                articleCache.insertAll(articleEntities)
            }
    }

    override fun getArticlesForFeed(feedUrl: String): Observable<List<Article>> {
        return articleCache.getArticlesForFeed(feedUrl)
            .map { articleEntities ->
                articleEntities.map { entity -> articleMapper.mapFromEntity(entity) }
            }
    }

}