package com.crowleysimon.data.model

data class Article(
    val title: String?,
    val link: String?,
    val pubDate: Long?,
    val description: String?,
    val image: String?,
    val author: String? = null,
    val category: String? = null,
    val channel: String? = null,
    val copyright: String? = null,
    val generator: String? = null,
    val guid: String,
    val item: String? = null,
    val lastBuildDate: String? = null,
    val managingEditor: String? = null,
    val ttl: String? = null,
    val read: Boolean?,
    val feedUrl: String,
    val feedTitle: String?,
)