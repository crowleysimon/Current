package com.crowleysimon.current.injection

import com.crowleysimon.current.ui.feed.FeedViewModel
import com.crowleysimon.current.ui.reader.ReaderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel<FeedViewModel>()
    viewModel<ReaderViewModel>()
}