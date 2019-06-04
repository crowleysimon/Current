package com.crowleysimon.remote.service

import com.crowleysimon.remote.model.RssFeedModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface RssService {

    @GET
    fun getArticlesForFeed(@Url feedUrl: String): Single<RssFeedModel>
}