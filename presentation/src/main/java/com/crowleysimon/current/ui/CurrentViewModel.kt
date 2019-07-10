package com.crowleysimon.current.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class CurrentViewModel<T>: ViewModel() {
    abstract fun observeData(): LiveData<T>
}