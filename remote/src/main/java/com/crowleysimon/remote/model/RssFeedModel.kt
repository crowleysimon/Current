package com.crowleysimon.remote.model

data class RssFeedModel (
    val feedTitle: String?,
    val items: List<RssItemModel>)