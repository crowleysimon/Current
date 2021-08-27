package com.crowleysimon.current.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crowleysimon.current.data.LiveEvent
import com.crowleysimon.current.data.LoadingResource
import com.crowleysimon.current.data.Resource
import com.crowleysimon.current.data.SuccessResource
import com.crowleysimon.current.ui.CurrentViewModel
import com.crowleysimon.current.ui.feed.item.toListItem
import com.crowleysimon.current.ui.feed.model.FeedUiModel
import com.crowleysimon.data.repository.ArticleRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FeedViewModel(
    private val repository: ArticleRepository,
) : CurrentViewModel<Resource<FeedUiModel>>() {

    private val liveData: MutableLiveData<Resource<FeedUiModel>> = MutableLiveData()

    val routerData: LiveEvent<Pair<String, String?>> = LiveEvent()

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
        viewModelScope.launch {
            repository.getAllArticles().collect { articles ->
                liveData.postValue(SuccessResource(FeedUiModel(articles.map { article ->
                    article.toListItem(::onArticleClicked)
                })))
            }
        }
    }

    /**
     * Calls a use case that forces the remote layer to update the LiveData.
     */
    fun refreshRepositories(feedUrl: String) {
        liveData.postValue(LoadingResource())
        viewModelScope.launch { repository.fetchArticlesForFeed(feedUrl) }
    }

    private fun onArticleClicked(articleGuid: String, feedId: String?) {
        routerData.postValue(Pair(articleGuid, feedId))
        //routing.routeToWithBundle(R.id.readerFragment, bundleOf("articleId" to articleGuid))
    }
}