package com.crowleysimon.current.ui.reader

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crowleysimon.current.data.Resource
import com.crowleysimon.domain.model.Article
import javax.inject.Inject

class ReaderViewModel @Inject constructor() : ViewModel() {

    private val liveData: MutableLiveData<Resource<Article>> = MutableLiveData()

    override fun onCleared() {

        super.onCleared()
    }

}