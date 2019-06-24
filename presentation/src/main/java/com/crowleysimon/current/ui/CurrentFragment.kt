package com.crowleysimon.current.ui

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class CurrentFragment<VM : ViewModel>(private val vmClass: Class<VM>) : DaggerFragment() {

    protected lateinit var viewModel: VM

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(vmClass)
    }
}
