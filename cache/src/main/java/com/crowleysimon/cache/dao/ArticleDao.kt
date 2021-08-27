package com.crowleysimon.cache.dao

import androidx.room.*
import com.crowleysimon.cache.model.CachedArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: CachedArticle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<CachedArticle>)

    @Query("SELECT * FROM CachedArticle ORDER BY pubDate DESC")
    fun getAllArticles(): Flow<List<CachedArticle>>

    @Query("SELECT * FROM CachedArticle WHERE guid = :articleId")
    suspend fun getArticle(articleId: String): CachedArticle

    @Query("SELECT * FROM CachedArticle WHERE feedTitle = :feedUrl")
    suspend fun getArticlesForFeed(feedUrl: String): List<CachedArticle>

    @Delete
    suspend fun delete(article: CachedArticle)

    @Update
    suspend fun update(article: CachedArticle)

}