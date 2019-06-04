package com.crowleysimon.data.mapper

import com.crowleysimon.data.model.ArticleEntity
import com.crowleysimon.domain.model.Article
import javax.inject.Inject

class ArticleMapper @Inject constructor() : EntityMapper<ArticleEntity, Article> {

    override fun mapFromEntity(entity: ArticleEntity): Article {
        return Article(
            title = entity.title,
            link = entity.link,
            pubDate = entity.pubDate,
            description = entity.description,
            image = entity.image,
            read = entity.read ?: false,
            feedUrl = entity.feedUrl,
            author = entity.author,
            category = entity.category,
            channel = entity.channel,
            copyright = entity.copyright,
            generator = entity.generator,
            guid = entity.guid,
            lastBuildDate = entity.lastBuildDate,
            managingEditor = entity.managingEditor,
            ttl = entity.ttl
        )
    }

    override fun mapToEntity(domain: Article): ArticleEntity {
        return ArticleEntity(
            title = domain.title,
            link = domain.link,
            pubDate = domain.pubDate,
            description = domain.description,
            image = domain.image,
            read = domain.read,
            feedUrl = domain.feedUrl,
            author = domain.author,
            category = domain.category,
            channel = domain.channel,
            copyright = domain.copyright,
            generator = domain.generator,
            guid = domain.guid,
            lastBuildDate = domain.lastBuildDate,
            managingEditor = domain.managingEditor,
            ttl = domain.ttl
        )
    }

}