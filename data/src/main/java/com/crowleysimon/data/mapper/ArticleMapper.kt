package com.crowleysimon.data.mapper

import com.crowleysimon.data.model.ArticleEntity
import com.crowleysimon.domain.model.Article
import javax.inject.Inject

class ArticleMapper @Inject constructor() : EntityMapper<ArticleEntity, Article> {
    override fun mapFromEntity(entity: ArticleEntity): Article {
        return Article(entity.title, entity.link, entity.pubDate, entity.description, entity.image, entity.read ?: false, entity.feedUrl)
    }

    override fun mapToEntity(domain: Article): ArticleEntity {
        return ArticleEntity(domain.title, domain.link, domain.pubDate, domain.description, domain.image, domain.read, domain.feedUrl)
    }

}