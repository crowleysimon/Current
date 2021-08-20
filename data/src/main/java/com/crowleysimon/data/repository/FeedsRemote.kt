package com.crowleysimon.data.repository

import com.crowleysimon.data.model.Article

interface FeedsRemote {

    suspend fun getArticlesForFeed(feedUrl: String): List<Article>
}