package com.crowleysimon.current.injection

import com.crowleysimon.data.repository.FeedsRemote
import com.crowleysimon.remote.FeedsRemoteImpl
import com.crowleysimon.remote.RssItemConverterFactory
import com.crowleysimon.remote.mapper.ArticleModelMapper
import com.crowleysimon.remote.mapper.RssItemMapper
import com.crowleysimon.remote.service.RssService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val remoteModule = module {
    single<FeedsRemote> { FeedsRemoteImpl(get(), get(), get()) }

    single<RssService> {
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://your.api.url/") //TODO: Needed something here to compile
            .addConverterFactory(RssItemConverterFactory.create())
            .build()
        retrofit.create(RssService::class.java)
    }

    single { ArticleModelMapper() }
    single { RssItemMapper() }
}