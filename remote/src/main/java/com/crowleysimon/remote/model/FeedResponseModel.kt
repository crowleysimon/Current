package com.crowleysimon.remote.model

data class FeedResponseModel(
    val feedModel: FeedModel,
    val articles: List<ArticleModel>
)