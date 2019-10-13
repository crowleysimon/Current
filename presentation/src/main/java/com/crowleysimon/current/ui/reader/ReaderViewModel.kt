package com.crowleysimon.current.ui.reader

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crowleysimon.current.data.ErrorResource
import com.crowleysimon.current.data.Resource
import com.crowleysimon.current.data.SuccessResource
import com.crowleysimon.current.ui.reader.item.ArticleCardItem
import com.crowleysimon.current.ui.reader.item.toCardItem
import com.crowleysimon.domain.interactor.GetArticle
import com.crowleysimon.domain.interactor.GetArticlesForFeed
import com.crowleysimon.domain.model.Article
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class ReaderViewModel @Inject constructor(
    private val getArticle: GetArticle,
    private val getAllArticlesForFeed: GetArticlesForFeed
) : ViewModel() {

    private val liveData: MutableLiveData<Resource<Article>> = MutableLiveData()

    private val relatedArticlesData: MutableLiveData<Resource<List<ArticleCardItem>>> = MutableLiveData()

    fun getArticle(articleID: String?) {
        return getArticle.execute(ArticleSubscriber(), articleID)
    }

    fun getArticles(feedId: String) {
        return getAllArticlesForFeed.execute(object : DisposableObserver<List<Article>>() {
            override fun onComplete() {

            }

            override fun onNext(t: List<Article>) {
                relatedArticlesData.postValue(SuccessResource(t.map { it.toCardItem(this@ReaderViewModel::getArticle) }))
            }

            override fun onError(e: Throwable) {
                relatedArticlesData.postValue(ErrorResource(e))
            }
        }, GetArticlesForFeed.Params.forFeed(feedId))
    }

    fun observeArticle(): LiveData<Resource<Article>> = liveData

    fun observeRelatedArticles(): LiveData<Resource<List<ArticleCardItem>>> = relatedArticlesData

    override fun onCleared() {
        getArticle.dispose()
        getAllArticlesForFeed.dispose()
        super.onCleared()
    }

    inner class ArticleSubscriber : DisposableObserver<Article>() {

        override fun onNext(t: Article) {
            liveData.postValue(SuccessResource(t))
        }

        override fun onComplete() {

        }

        override fun onError(e: Throwable) {
            liveData.postValue(ErrorResource(e))
        }
    }
}