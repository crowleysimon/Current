package com.crowleysimon.current.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crowleysimon.current.data.LiveEvent
import com.crowleysimon.current.data.LoadingResource
import com.crowleysimon.current.data.Resource
import com.crowleysimon.current.data.SuccessResource
import com.crowleysimon.current.ui.feed.item.toListItem
import com.crowleysimon.current.ui.feed.model.FeedUiModel
import com.crowleysimon.data.repository.ArticleRepository
import kotlinx.coroutines.launch

class FeedViewModel(
    private val repository: ArticleRepository,
) : ViewModel() {

    private var _state: MutableLiveData<Resource<FeedUiModel>> = MutableLiveData()
    val state: LiveData<Resource<FeedUiModel>> = _state

    val routerData: LiveEvent<Pair<String, String?>> = LiveEvent()

    /**
     * Will call a use case that has the data layer update results that will be returned to the LiveData.
     * Any observers subscribing to the live data will updated of the results.
     */
    fun fetchArticles() {
        _state.postValue(LoadingResource())
        viewModelScope.launch {
            repository.getAllArticles().collect { articles ->
                _state.postValue(SuccessResource(FeedUiModel(articles.map { article ->
                    article.toListItem(::onArticleClicked)
                })))
            }
        }
    }

    /**
     * Calls a use case that forces the remote layer to update the LiveData.
     */
    fun refreshRepositories(feedUrl: String) {
        _state.postValue(LoadingResource())
        viewModelScope.launch { repository.fetchArticlesForFeed(feedUrl) }
    }

    private fun onArticleClicked(articleGuid: String, feedId: String?) {
        routerData.postValue(Pair(articleGuid, feedId))
        //routing.routeToWithBundle(R.id.readerFragment, bundleOf("articleId" to articleGuid))
    }
}