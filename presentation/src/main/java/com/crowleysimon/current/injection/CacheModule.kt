package com.crowleysimon.current.injection

import com.crowleysimon.cache.ArticleCacheImpl
import com.crowleysimon.cache.RssDatabase
import com.crowleysimon.cache.mapper.CachedArticleMapper
import com.crowleysimon.data.repository.ArticlesCache
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val cacheModule = module {

    single<ArticlesCache> { ArticleCacheImpl(get(), get()) }

    single { CachedArticleMapper() }

    single { RssDatabase.getInstance(androidApplication()) }

    single { get<RssDatabase>().articleDao() }

}