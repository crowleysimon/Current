package com.crowleysimon.domain.model

data class Article (
    val title: String?,
    val link: String?,
    val pubDate: String?,
    val description: String?,
    val image: String?,
    var read: Boolean,
    val feedUrl: String
)