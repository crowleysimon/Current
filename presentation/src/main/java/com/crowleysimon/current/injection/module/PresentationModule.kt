package com.crowleysimon.current.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crowleysimon.current.data.UiThread
import com.crowleysimon.current.injection.ViewModelFactory
import com.crowleysimon.current.injection.key.ViewModelKey
import com.crowleysimon.current.ui.MainActivity
import com.crowleysimon.current.ui.feed.FeedViewModel
import com.crowleysimon.current.ui.reader.ReaderViewModel
import com.crowleysimon.domain.executor.PostExecutionThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PresentationModule {

    @Binds
    abstract fun bindExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributesMainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun bindFeedViewModel(viewModel: FeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReaderViewModel::class)
    abstract fun bindReaderViewModel(viewModel: ReaderViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}