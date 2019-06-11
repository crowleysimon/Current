package com.crowleysimon.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CachedArticle(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
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
    val guid: String? = null,
    val item: String? = null,
    val lastBuildDate: String? = null,
    val managingEditor: String? = null,
    val ttl: String? = null,
    val read: Boolean,
    val feedUrl: String
)