package com.crowleysimon.current.ui.reader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.crowleysimon.current.R
import com.crowleysimon.current.data.ErrorResource
import com.crowleysimon.current.data.LoadingResource
import com.crowleysimon.current.data.Resource
import com.crowleysimon.current.data.SuccessResource
import com.crowleysimon.current.ui.CurrentFragment
import com.crowleysimon.domain.model.Article

class ReaderFragment: CurrentFragment<ReaderViewModel>(ReaderViewModel::class.java) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reader, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
        setupViews()
    }

    private fun bindObservers() {

    }

    private fun handleRepositoryDataState(resource: Resource<List<Article>>) {
        when (resource) {
            is LoadingResource -> setupScreenForLoadingState()
            is SuccessResource -> setupScreenForSuccessState(resource.data)
            is ErrorResource -> setupScreenForErrorState(resource.throwable)
        }
    }

    private fun setupScreenForLoadingState() {

    }

    private fun setupScreenForSuccessState(data: List<Article>) {

    }

    private fun setupScreenForErrorState(throwable: Throwable) {

        // Not great but just for demo purposes, would normally log error with Timber
        Toast.makeText(context, throwable.localizedMessage, Toast.LENGTH_LONG).show()
    }

    private fun setupViews() {

    }
}