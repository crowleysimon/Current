package com.crowleysimon.current.ui.reader

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crowleysimon.current.data.Resource
import com.crowleysimon.current.data.SuccessResource
import com.crowleysimon.current.ui.reader.item.ArticleCardItem
import com.crowleysimon.current.ui.reader.item.toCardItem
import com.crowleysimon.data.model.Article
import com.crowleysimon.data.repository.ArticleRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReaderViewModel @Inject constructor(
    private val repository: ArticleRepository,
) : ViewModel() {

    private val liveData: MutableLiveData<Resource<Article>> = MutableLiveData()

    private val relatedArticlesData: MutableLiveData<Resource<List<ArticleCardItem>>> =
        MutableLiveData()

    fun getArticle(articleID: String) {
        viewModelScope.launch {
            liveData.postValue(SuccessResource(repository.getArticle(articleID)))
        }
    }

    fun getArticles(feedId: String) {
        viewModelScope.launch {
            val articles = repository.getArticlesForFeed(feedId)
                .map { it.toCardItem(this@ReaderViewModel::getArticle) }
            relatedArticlesData.postValue(SuccessResource(articles))
        }
    }

    fun observeArticle(): LiveData<Resource<Article>> = liveData

    fun observeRelatedArticles(): LiveData<Resource<List<ArticleCardItem>>> = relatedArticlesData
}