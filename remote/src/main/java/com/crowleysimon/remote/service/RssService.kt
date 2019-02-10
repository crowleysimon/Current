package com.crowleysimon.remote.service

import io.reactivex.Single
import me.toptas.rssconverter.RssFeed
import retrofit2.http.GET
import retrofit2.http.Url

interface RssService {

    @GET
    fun getArticlesForFeed(@Url feedUrl: String): Single<RssFeed>
}