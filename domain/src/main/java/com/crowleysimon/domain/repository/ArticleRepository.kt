package com.crowleysimon.domain.repository

import com.crowleysimon.domain.model.Article
import io.reactivex.Completable
import io.reactivex.Observable

interface ArticleRepository {

    fun getArticlesForFeed(feedUrl: String): Observable<List<Article>>

    fun getAllArticles(): Observable<List<Article>>

    fun getArticle(articleId: String): Observable<Article>

    fun fetchArticlesForFeed(feedUrl: String): Completable
}