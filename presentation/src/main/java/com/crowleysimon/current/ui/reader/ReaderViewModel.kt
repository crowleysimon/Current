package com.crowleysimon.current.ui.reader

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crowleysimon.current.data.ErrorResource
import com.crowleysimon.current.data.Resource
import com.crowleysimon.current.data.SuccessResource
import com.crowleysimon.domain.interactor.GetArticle
import com.crowleysimon.domain.model.Article
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class ReaderViewModel @Inject constructor(private val getArticle: GetArticle) : ViewModel() {

    private val liveData: MutableLiveData<Resource<Article>> = MutableLiveData()

    fun getArticle(articleID: String?) {
        return getArticle.execute(ArticleSubscriber(), articleID)
    }

    fun observeArticle(): LiveData<Resource<Article>> {
        return liveData
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