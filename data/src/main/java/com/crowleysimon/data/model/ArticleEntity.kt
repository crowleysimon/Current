package com.crowleysimon.data.model

data class ArticleEntity(
    val title: String?,
    val link: String?,
    val pubDate: String?,
    val description: String?,
    val image: String?,
    val read: Boolean?,
    val feedUrl: String
)