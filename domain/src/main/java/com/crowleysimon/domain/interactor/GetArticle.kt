package com.crowleysimon.domain.interactor

import com.crowleysimon.domain.executor.PostExecutionThread
import com.crowleysimon.domain.model.Article
import com.crowleysimon.domain.repository.ArticleRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetArticle @Inject constructor(
    private val articleRepository: ArticleRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<Article, String>(postExecutionThread) {

    override fun buildUseCaseObservable(params: String?): Observable<Article> {
        if (params == null) {
            return Observable.empty()
        }
        return articleRepository.getArticle(params)
    }

}