package com.crowleysimon.current.ui.feed

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.crowleysimon.current.R
import com.crowleysimon.current.data.ErrorResource
import com.crowleysimon.current.data.LoadingResource
import com.crowleysimon.current.data.Resource
import com.crowleysimon.current.data.SuccessResource
import com.crowleysimon.current.injection.ViewModelFactory
import com.crowleysimon.domain.model.Article
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_feed.*
import javax.inject.Inject

class FeedActivity : AppCompatActivity(), ArticleViewHolder.ActionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var feedViewModel: FeedViewModel
    private var feedAdapter: FeedAdapter = FeedAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        AndroidInjection.inject(this)
        bindViewModels()
        bindObservers()
        setupViews()
    }

    private fun bindViewModels() {
        feedViewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)
    }

    private fun bindObservers() {
        feedViewModel.getArticles().observe(this, Observer<Resource<List<Article>>> {
            it?.let { resource ->
                handleRepositoryDataState(resource)
            }
        })
        feedViewModel.refreshRepositories("https://www.androidpolice.com/feed")
        feedViewModel.fetchArticles()
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

        // Not great but just for demo purposes, would normally log error with Timber
        Toast.makeText(this, throwable.localizedMessage, Toast.LENGTH_LONG).show()
    }

    private fun setupViews() {
        feedRecyclerView.layoutManager = LinearLayoutManager(this)
        feedAdapter.actionListener = this
        feedRecyclerView.adapter = feedAdapter
    }

    override fun onArticleClicked(articleUrl: String?) {
        if (articleUrl == null) {
            return
        }
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(articleUrl)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.no_app_found), Toast.LENGTH_LONG).show()
        }
    }
}