package com.crowleysimon.domain.interactor

import com.crowleysimon.domain.executor.PostExecutionThread
import com.crowleysimon.domain.model.Article
import com.crowleysimon.domain.repository.ArticleRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetArticlesForFeed @Inject constructor(
    private val articleRepository: ArticleRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Article>, GetArticlesForFeed.Params>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Observable<List<Article>> {
        if (params == null) throw IllegalArgumentException("Params can't be null")
        return articleRepository.getArticlesForFeed(params.feedUrl)
    }


    data class Params(val feedUrl: String) {
        companion object {
            fun forFeed(feedUrl: String): Params {
                return Params(feedUrl)
            }
        }
    }
}