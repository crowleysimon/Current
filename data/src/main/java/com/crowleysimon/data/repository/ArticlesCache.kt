package com.crowleysimon.data.repository

import com.crowleysimon.data.model.ArticleEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface ArticlesCache {

    fun insert(article: ArticleEntity): Completable

    fun insertAll(articles: List<ArticleEntity>): Completable

    fun getAllArticles(): Observable<List<ArticleEntity>>

    fun getArticlesForFeed(feedUrl: String): Observable<List<ArticleEntity>>

    fun delete(article: ArticleEntity): Completable

    fun update(article: ArticleEntity): Completable
}