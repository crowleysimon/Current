package com.crowleysimon.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CachedArticle(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String?,
    var link: String?,
    var pubDate: String?,
    var description: String?,
    var image: String?,
    var read: Boolean,
    var feedUrl: String
)