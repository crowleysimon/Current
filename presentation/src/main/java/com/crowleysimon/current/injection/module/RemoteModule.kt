package com.crowleysimon.current.injection.module

import com.crowleysimon.data.repository.FeedsRemote
import com.crowleysimon.remote.FeedsRemoteImpl
import com.crowleysimon.remote.RssItemConverterFactory
import com.crowleysimon.remote.service.RssService
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideRssService(): RssService {
            val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://your.api.url/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(RssItemConverterFactory.create())
                .build()
            return retrofit.create(RssService::class.java)
        }
    }

    @Binds
    abstract fun bindFeedsRemote(feedsRemoteImpl: FeedsRemoteImpl): FeedsRemote
}