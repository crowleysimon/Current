package com.crowleysimon.current.injection.module

import com.crowleysimon.current.ui.feed.FeedFragment
import com.crowleysimon.current.ui.reader.ReaderFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesFeedFragment(): FeedFragment

    @ContributesAndroidInjector
    abstract fun contributesReaderFragment(): ReaderFragment

    /*@ContributesAndroidInjector()//modules = [RoutingModule::class]
    abstract fun contributesRouterFragment(): RouterFragment*/
}