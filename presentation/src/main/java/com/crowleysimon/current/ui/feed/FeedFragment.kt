package com.crowleysimon.current.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding

    val viewModel: FeedViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater)
        return binding.root
    }

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
        viewModel.observeData()
            .observe(viewLifecycleOwner, Observer(this::handleRepositoryDataState))
        viewModel.routerData.observe(viewLifecycleOwner, Observer(this::routeTo))
    }

    private fun routeTo(routeInfo: Pair<String, String?>) {
        findNavController().navigate(
            R.id.readerFragment,
            bundleOf("articleId" to routeInfo.first, "feedId" to routeInfo.second)
        )
    }

    private fun handleRepositoryDataState(resource: Resource<FeedUiModel>) {
        when (resource) {
            is LoadingResource -> setupScreenForLoadingState()
            is SuccessResource -> setupScreenForSuccessState(resource.data)
            is ErrorResource -> setupScreenForErrorState(resource.throwable)
        }
    }

    private fun setupScreenForLoadingState() {
        binding.progress.visibility = View.VISIBLE
        binding.feedRecyclerView.visibility = View.GONE
    }

    private fun setupScreenForSuccessState(data: FeedUiModel) {
        binding.progress.visibility = View.GONE
        binding.feedRecyclerView.visibility = View.VISIBLE
        if (binding.feedRecyclerView.adapter == null) {
            binding.feedRecyclerView.adapter =
                GroupAdapter<GroupieViewHolder>().apply { addAll(data.articles) }
        } else {
            (binding.feedRecyclerView.adapter as GroupAdapter).updateAsync(data.articles)
        }
    }

    private fun setupScreenForErrorState(throwable: Throwable) {
        binding.progress.visibility = View.GONE
        binding.feedRecyclerView.visibility = View.GONE
        Timber.e(throwable)
    }
}