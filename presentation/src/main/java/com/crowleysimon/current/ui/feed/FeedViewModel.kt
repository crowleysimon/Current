package com.crowleysimon.current.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crowleysimon.current.data.ErrorResource
import com.crowleysimon.current.data.LoadingResource
import com.crowleysimon.current.data.Resource
import com.crowleysimon.current.data.SuccessResource
import com.crowleysimon.domain.interactor.FetchArticlesForFeed
import com.crowleysimon.domain.interactor.GetAllArticles
import com.crowleysimon.domain.model.Article
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val fetchArticlesForFeed: FetchArticlesForFeed,
    private val getAllArticles: GetAllArticles
) : ViewModel() {

    private val liveData: MutableLiveData<Resource<List<Article>>> = MutableLiveData()

    override fun onCleared() {
        fetchArticlesForFeed.dispose()
        getAllArticles.dispose()
        super.onCleared()
    }

    /**
     * Returns the livedata for the view model, does not actually update or modify any results
     */
    fun getArticles(): LiveData<Resource<List<Article>>> {
        return liveData
    }

    /**
     * Will call a use case that has the data layer update results that will be returned to the livedata.
     * Any observers subscribing to the live data will updated of the results.
     */
    fun fetchArticles() {
        liveData.postValue(LoadingResource())
        return getAllArticles.execute(ArticlesSubscriber())
    }

    /**
     * Calls a use case that forces the remote layer to update the livedata.
     */
    fun refreshRepositories(feedUrl: String) {
        liveData.postValue(LoadingResource())
        return fetchArticlesForFeed.execute(
            RefreshSubscriber(),
            FetchArticlesForFeed.Params.forFeed(feedUrl)
        )
    }

    inner class ArticlesSubscriber : DisposableObserver<List<Article>>() {
        override fun onComplete() {

        }

        override fun onNext(t: List<Article>) {
            liveData.postValue(SuccessResource(t))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(ErrorResource(e))
        }
    }

    inner class RefreshSubscriber : DisposableCompletableObserver() {
        override fun onComplete() {

        }

        override fun onError(e: Throwable) {
            liveData.postValue(ErrorResource(e))
        }

    }
}