package com.crowleysimon.current.injection.module

import com.crowleysimon.current.ui.feed.FeedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesFeedFragment(): FeedFragment
}