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
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ReaderViewModel(
    private val repository: ArticleRepository,
) : ViewModel() {

    private var _articles: MutableLiveData<Resource<Article>> = MutableLiveData()
    val articles: LiveData<Resource<Article>> = _articles

    private var _relatedArticles: MutableLiveData<Resource<List<ArticleCardItem>>> =
        MutableLiveData()
    val relatedArticles: LiveData<Resource<List<ArticleCardItem>>> = _relatedArticles

    fun getArticle(articleID: String) {
        viewModelScope.launch {
            _articles.postValue(SuccessResource(repository.getArticle(articleID)))
        }
    }

    fun getArticles(feedId: String) {
        viewModelScope.launch {
            val articles = repository.getArticlesForFeed(feedId)
                .map { it.toCardItem(this@ReaderViewModel::getArticle) }
            _relatedArticles.postValue(SuccessResource(articles))
        }
    }

    fun markArticleRead(articleID: String) {
        viewModelScope.launch { repository.markArticleRead(articleID) }
    }
}