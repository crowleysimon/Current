package com.crowleysimon.current.ui.reader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.crowleysimon.current.R
import com.crowleysimon.current.data.ErrorResource
import com.crowleysimon.current.data.LoadingResource
import com.crowleysimon.current.data.Resource
import com.crowleysimon.current.data.SuccessResource
import com.crowleysimon.current.ui.CurrentFragment
import com.crowleysimon.domain.model.Article
import kotlinx.android.synthetic.main.fragment_reader.*
import timber.log.Timber

class ReaderFragment: CurrentFragment<ReaderViewModel>(ReaderViewModel::class.java) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reader, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
        viewModel.getArticle(arguments?.getString("articleId", ""))
    }

    private fun bindObservers() {
        viewModel.observeArticle().observe(this, Observer {
            handleRepositoryDataState(it)
        })
    }

    private fun handleRepositoryDataState(resource: Resource<Article>) {
        when (resource) {
            is LoadingResource -> setupScreenForLoadingState()
            is SuccessResource -> setupScreenForSuccessState(resource.data)
            is ErrorResource -> setupScreenForErrorState(resource.throwable)
        }
    }

    private fun setupScreenForLoadingState() {

    }

    private fun setupScreenForSuccessState(data: Article) {
        readerWebView.loadUrl(data.link ?: "")
        /*val encodedHtml = Base64.encodeToString(data.description?.toByteArray(), Base64.NO_PADDING)
        readerWebView.loadData(encodedHtml, "text/html", "base64")*/
    }

    private fun setupScreenForErrorState(throwable: Throwable) {
        Timber.e(throwable)
    }
}