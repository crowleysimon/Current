package com.crowleysimon.remote.service

import com.crowleysimon.remote.model.RssFeedModel
import retrofit2.http.GET
import retrofit2.http.Url

interface RssService {

    @GET
    suspend fun getArticlesForFeed(@Url feedUrl: String): RssFeedModel
}