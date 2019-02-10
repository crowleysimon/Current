package com.crowleysimon.domain.interactor

import com.crowleysimon.domain.executor.PostExecutionThread
import com.crowleysimon.domain.repository.ArticleRepository
import io.reactivex.Completable
import javax.inject.Inject

open class FetchArticlesForFeed @Inject constructor(
    private val articleRepository: ArticleRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<FetchArticlesForFeed.Params>(postExecutionThread) {

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can't be null")
        return articleRepository.fetchArticlesForFeed(params.feedUrl)
    }


    data class Params(val feedUrl: String) {
        companion object {
            fun forFeed(feedUrl: String): Params {
                return Params(feedUrl)
            }
        }
    }
}