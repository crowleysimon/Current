package com.crowleysimon.current.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crowleysimon.current.data.*
import com.crowleysimon.current.ui.CurrentViewModel
import com.crowleysimon.current.ui.feed.item.toListItem
import com.crowleysimon.current.ui.feed.model.FeedUiModel
import com.crowleysimon.domain.interactor.FetchArticlesForFeed
import com.crowleysimon.domain.interactor.GetAllArticles
import com.crowleysimon.domain.model.Article
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val fetchArticlesForFeed: FetchArticlesForFeed,
    private val getAllArticles: GetAllArticles
    //private val routing: Routing
) : CurrentViewModel<Resource<FeedUiModel>>() {

    private val liveData: MutableLiveData<Resource<FeedUiModel>> = MutableLiveData()

    val routerData: LiveEvent<Pair<String, String?>> = LiveEvent()

    override fun onCleared() {
        fetchArticlesForFeed.dispose()
        getAllArticles.dispose()
        super.onCleared()
    }

    /**
     * Returns the LiveData for the view model, does not actually update or modify any results
     */
    override fun observeData(): LiveData<Resource<FeedUiModel>> {
        return liveData
    }

    /**
     * Will call a use case that has the data layer update results that will be returned to the LiveData.
     * Any observers subscribing to the live data will updated of the results.
     */
    fun fetchArticles() {
        liveData.postValue(LoadingResource())
        return getAllArticles.execute(ArticlesSubscriber())
    }

    /**
     * Calls a use case that forces the remote layer to update the LiveData.
     */
    fun refreshRepositories(feedUrl: String) {
        liveData.postValue(LoadingResource())
        return fetchArticlesForFeed.execute(
            RefreshSubscriber(),
            FetchArticlesForFeed.Params.forFeed(feedUrl)
        )
    }

    private fun onArticleClicked(articleGuid: String, feedId: String?) {
        routerData.postValue(Pair(articleGuid, feedId))
        //routing.routeToWithBundle(R.id.readerFragment, bundleOf("articleId" to articleGuid))
    }

    inner class ArticlesSubscriber : DisposableObserver<List<Article>>() {
        override fun onComplete() {

        }

        override fun onNext(articles: List<Article>) {
            liveData.postValue(SuccessResource(FeedUiModel(articles.map { article ->
                article.toListItem(::onArticleClicked)
            })))
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