package com.crowleysimon.current.ui.feed

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
import com.crowleysimon.current.ui.CompositeAdapter
import com.crowleysimon.current.ui.CurrentFragment
import com.crowleysimon.current.ui.addBinding
import com.crowleysimon.current.ui.feed.model.ArticleListItem
import com.crowleysimon.current.ui.feed.model.FeedUiModel
import kotlinx.android.synthetic.main.fragment_feed.*
import timber.log.Timber

class FeedFragment: CurrentFragment<FeedViewModel>(FeedViewModel::class.java) {

    private var adapter = CompositeAdapter<ArticleListItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
        setupViews()

        viewModel.refreshRepositories("https://www.theverge.com/rss/index.xml")
        //viewModel.refreshRepositories("https://www.androidpolice.com/feed")
        //viewModel.refreshRepositories("https://www.anandtech.com/rss/")
        viewModel.fetchArticles()
    }

    private fun bindObservers() {
        viewModel.observeData().observe(this, Observer(this::handleRepositoryDataState))
    }

    private fun handleRepositoryDataState(resource: Resource<FeedUiModel>) {
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

    private fun setupScreenForSuccessState(data: FeedUiModel) {
        progress.visibility = View.GONE
        feedRecyclerView.visibility = View.VISIBLE
        adapter.items = data.articles
        adapter.notifyDataSetChanged()
    }

    private fun setupScreenForErrorState(throwable: Throwable) {
        progress.visibility = View.GONE
        feedRecyclerView.visibility = View.GONE

        Timber.e(throwable)
    }

    private fun setupViews() {
        adapter.addBinding { ArticleViewHolder.make(feedRecyclerView) }
        feedRecyclerView.adapter = adapter
    }

    /*override fun onArticleClicked(articleId: String?) {
        findNavController().navigate(R.id.readerFragment, bundleOf("articleId" to articleId))
    }*/

}