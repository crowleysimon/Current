package com.crowleysimon.domain.interactor

import com.crowleysimon.domain.executor.PostExecutionThread
import com.crowleysimon.domain.model.Article
import com.crowleysimon.domain.repository.ArticleRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetAllArticles @Inject constructor(
    private val articleRepository: ArticleRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Article>, Nothing?>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Nothing?): Observable<List<Article>> {
        return articleRepository.getAllArticles()
    }

}