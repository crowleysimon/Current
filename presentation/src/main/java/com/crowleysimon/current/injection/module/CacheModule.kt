package com.crowleysimon.current.injection.module

import android.app.Application
import com.crowleysimon.cache.ArticleCacheImpl
import com.crowleysimon.cache.RssDatabase
import com.crowleysimon.data.repository.ArticlesCache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesAppDatabase(application: Application): RssDatabase {
            return RssDatabase.getInstance(application)
        }
    }

    @Binds
    abstract fun bindArticlesCache(articleCacheImpl: ArticleCacheImpl): ArticlesCache
}