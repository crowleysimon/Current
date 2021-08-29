package com.crowleysimon.remote.model

import java.io.Serializable

data class RssItemModel(
    val author: String? = null,
    val category: String? = null,
    val channel: String? = null,
    val copyright: String? = null,
    val description: String? = null,
    val generator: String? = null,
    val guid: String,
    val image: String? = null,
    val item: String? = null,
    val lastBuildDate: String? = null,
    val link: String? = null,
    val managingEditor: String? = null,
    val pubDate: String? = null,
    val title: String? = null,
    val ttl: String? = null
) : Serializable

