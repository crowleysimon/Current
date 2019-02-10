package com.crowleysimon.current.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crowleysimon.current.data.UiThread
import com.crowleysimon.current.injection.ViewModelFactory
import com.crowleysimon.current.injection.key.ViewModelKey
import com.crowleysimon.current.ui.feed.FeedActivity
import com.crowleysimon.current.ui.feed.FeedViewModel
import com.crowleysimon.domain.executor.PostExecutionThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PresentationModule {

    @Binds
    abstract fun bindExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesFeedActivity(): FeedActivity


    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun bindFeedViewModel(viewModel: FeedViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}