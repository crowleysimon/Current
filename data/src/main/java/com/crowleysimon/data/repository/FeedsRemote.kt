package com.crowleysimon.data.repository

import com.crowleysimon.data.model.ArticleEntity
import io.reactivex.Single

interface FeedsRemote {

    fun getArticlesForFeed(feedUrl: String): Single<List<ArticleEntity>>
}