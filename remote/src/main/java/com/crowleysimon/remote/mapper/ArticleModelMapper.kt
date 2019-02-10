package com.crowleysimon.remote.mapper

import com.crowleysimon.data.model.ArticleEntity
import com.crowleysimon.remote.model.ArticleModel
import javax.inject.Inject

class ArticleModelMapper @Inject constructor() : ModelMapper<ArticleModel, ArticleEntity> {
    override fun mapFromResponse(response: ArticleModel): ArticleEntity {
        return ArticleEntity(
            response.title,
            response.link,
            response.pubDate,
            response.description,
            response.image,
            false,
            response.feedUrl
        )
    }
}