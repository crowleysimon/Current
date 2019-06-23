package com.crowleysimon.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.crowleysimon.cache.dao.ArticleDao
import com.crowleysimon.cache.model.CachedArticle

@Database(
    version = 2,
    entities = [
        CachedArticle::class
    ]
)
abstract class RssDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: RssDatabase? = null

        fun getInstance(context: Context): RssDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): RssDatabase {
            return Room.databaseBuilder(context.applicationContext, RssDatabase::class.java, "rss-database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}