package com.crowleysimon.cache.dao

import androidx.room.*
import com.crowleysimon.cache.model.CachedArticle
import io.reactivex.Observable

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: CachedArticle): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<CachedArticle>): List<Long>

    @Query("SELECT * FROM CachedArticle ORDER BY pubDate DESC")
    fun getAllArticles(): Observable<List<CachedArticle>>

    @Query("SELECT * FROM CachedArticle WHERE guid = :articleId")
    fun getArticle(articleId: String): Observable<CachedArticle>

    @Query("SELECT * FROM CachedArticle WHERE feedUrl = :feedUrl")
    fun getArticlesForFeed(feedUrl: String): Observable<List<CachedArticle>>

    @Delete
    fun delete(article: CachedArticle)

    @Update
    fun update(article: CachedArticle)

}