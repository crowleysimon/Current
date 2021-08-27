package com.crowleysimon.current.ui.feed

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.crowleysimon.current.R
import com.crowleysimon.current.data.ErrorResource
import com.crowleysimon.current.data.LoadingResource
import com.crowleysimon.current.data.Resource
import com.crowleysimon.current.data.SuccessResource
import com.crowleysimon.current.databinding.FragmentFeedBinding
import com.crowleysimon.current.ui.feed.model.FeedUiModel
import com.crowleysimon.current.ui.viewBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private val binding by viewBinding(FragmentFeedBinding::bind)
    private val viewModel: FeedViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
        //TODO:
        viewModel.refreshRepositories("https://www.theverge.com/rss/index.xml")
        //viewModel.refreshRepositories("https://www.anandtech.com/rss/")
        viewModel.refreshRepositories("https://www.polygon.com/rss/index.xml")
        viewModel.refreshRepositories("https://www.vox.com/rss/index.xml")
        viewModel.fetchArticles()
    }

    private fun bindObservers() {
        viewModel.state.observe(viewLifecycleOwner, Observer(this::handleRepositoryDataState))
        viewModel.routerData.observe(viewLifecycleOwner, Observer(this::routeTo))
    }

    private fun routeTo(routeInfo: Pair<String, String?>) = findNavController().navigate(
        R.id.readerFragment,
        bundleOf("articleId" to routeInfo.first, "feedId" to routeInfo.second)
    )

    private fun handleRepositoryDataState(resource: Resource<FeedUiModel>) = when (resource) {
        is LoadingResource -> setupScreenForLoadingState()
        is SuccessResource -> setupScreenForSuccessState(resource.data)
        is ErrorResource -> setupScreenForErrorState(resource.throwable)
    }

    private fun setupScreenForLoadingState() = with(binding) {
        progress.visibility = View.VISIBLE
        feedRecyclerView.visibility = View.GONE
    }

    private fun setupScreenForSuccessState(data: FeedUiModel) = with(binding) {
        progress.visibility = View.GONE
        feedRecyclerView.visibility = View.VISIBLE
        if (feedRecyclerView.adapter == null) {
            feedRecyclerView.adapter =
                GroupAdapter<GroupieViewHolder>().apply { addAll(data.articles) }
        } else {
            (feedRecyclerView.adapter as GroupAdapter).updateAsync(data.articles)
        }
    }

    private fun setupScreenForErrorState(throwable: Throwable) = with(binding) {
        progress.visibility = View.GONE
        feedRecyclerView.visibility = View.GONE
        Timber.e(throwable)
    }
}