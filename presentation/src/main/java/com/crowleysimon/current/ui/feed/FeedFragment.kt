package com.crowleysimon.current.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.crowleysimon.current.R
import com.crowleysimon.current.data.ErrorResource
import com.crowleysimon.current.data.LoadingResource
import com.crowleysimon.current.data.Resource
import com.crowleysimon.current.data.SuccessResource
import com.crowleysimon.current.ui.CurrentFragment
import com.crowleysimon.domain.model.Article
import kotlinx.android.synthetic.main.fragment_feed.*
import timber.log.Timber

class FeedFragment: CurrentFragment<FeedViewModel>(FeedViewModel::class.java), ArticleViewHolder.ActionListener {

    private var feedAdapter: FeedAdapter = FeedAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
        setupViews()
    }

    private fun bindObservers() {
        viewModel.getArticles().observe(this, Observer<Resource<List<Article>>> {
            it?.let { resource ->
                handleRepositoryDataState(resource)
            }
        })
        viewModel.refreshRepositories("https://www.theverge.com/rss/index.xml")
        viewModel.fetchArticles()
    }

    private fun handleRepositoryDataState(resource: Resource<List<Article>>) {
        when (resource) {
            is LoadingResource -> setupScreenForLoadingState()
            is SuccessResource -> setupScreenForSuccessState(resource.data)
            is ErrorResource -> setupScreenForErrorState(resource.throwable)
        }
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        feedRecyclerView.visibility = View.GONE
    }

    private fun setupScreenForSuccessState(data: List<Article>) {
        progress.visibility = View.GONE
        feedRecyclerView.visibility = View.VISIBLE
        feedAdapter.data = data
        feedAdapter.notifyDataSetChanged()
    }

    private fun setupScreenForErrorState(throwable: Throwable) {
        progress.visibility = View.GONE
        feedRecyclerView.visibility = View.GONE

        Timber.e(throwable)
    }

    private fun setupViews() {
        feedRecyclerView.layoutManager = LinearLayoutManager(context!!)
        feedAdapter.actionListener = this
        feedRecyclerView.adapter = feedAdapter
    }

    override fun onArticleClicked(articleUrl: String?) {
        //TODO:
    }

}