package com.crowleysimon.current.injection

import com.crowleysimon.cache.ArticleCacheImpl
import com.crowleysimon.data.ArticlesDataRepository
import com.crowleysimon.data.repository.ArticleRepository
import com.crowleysimon.data.repository.ArticlesCache
import com.crowleysimon.data.repository.FeedsRemote
import com.crowleysimon.remote.FeedsRemoteImpl
import org.koin.dsl.module

val dataModule = module {
    single<ArticleRepository> { ArticlesDataRepository(get(), get()) }

    single<ArticlesCache> { ArticleCacheImpl(get(), get()) }

    single<FeedsRemote> { FeedsRemoteImpl(get(), get(), get()) }
}