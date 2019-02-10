package com.crowleysimon.current.injection.module

import com.crowleysimon.data.ArticlesDataRepository
import com.crowleysimon.domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(articlesRepository: ArticlesDataRepository): ArticleRepository
}