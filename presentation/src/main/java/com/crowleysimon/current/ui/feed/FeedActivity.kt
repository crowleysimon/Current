package com.crowleysimon.current.ui.feed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.aesthetic.Aesthetic
import com.afollestad.aesthetic.AutoSwitchMode
import com.crowleysimon.current.R
import com.crowleysimon.current.data.ErrorResource
import com.crowleysimon.current.data.LoadingResource
import com.crowleysimon.current.data.Resource
import com.crowleysimon.current.data.SuccessResource
import com.crowleysimon.current.injection.ViewModelFactory
import com.crowleysimon.domain.model.Article
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_feed.*
import timber.log.Timber
import javax.inject.Inject

class FeedActivity : AppCompatActivity(), ArticleViewHolder.ActionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var feedViewModel: FeedViewModel
    private var feedAdapter: FeedAdapter = FeedAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        Aesthetic.attach(this)  // MUST come before super.onCreate(...)
        super.onCreate(savedInstanceState)

        // If we haven't set any defaults, do that now
        //if (Aesthetic.isFirstTime) {
            Aesthetic.config {
                isDark(false)
                lightStatusBarMode(AutoSwitchMode.AUTO)
                lightNavigationBarMode(AutoSwitchMode.AUTO)
                colorStatusBar(res = R.color.white)
                colorNavigationBar(res = R.color.white)
                colorWindowBackground(res = R.color.window_background)
                textColorPrimary(res = R.color.text_color_primary)
                textColorSecondary(res = R.color.text_color_secondary)
                textColorPrimaryInverse(res = R.color.text_color_primary_dark)
                textColorSecondaryInverse(res = R.color.text_color_secondary_dark)
            }
        //}

        setContentView(R.layout.activity_feed)
        AndroidInjection.inject(this)
        bindViewModels()
        bindObservers()
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        Aesthetic.resume(this)
    }

    override fun onPause() {
        Aesthetic.pause(this)
        super.onPause()
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
        feedViewModel.refreshRepositories("https://www.theverge.com/rss/index.xml")
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

        Timber.e(throwable)
    }

    private fun setupViews() {
        feedRecyclerView.layoutManager = LinearLayoutManager(this)
        feedAdapter.actionListener = this
        feedRecyclerView.adapter = feedAdapter
    }

    override fun onArticleClicked(articleUrl: String?) {
        /*if (articleUrl == null) {
            return
        }
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(articleUrl)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.no_app_found), Toast.LENGTH_LONG).show()
        }*/
        Aesthetic.config {
            isDark(false)
            lightStatusBarMode(AutoSwitchMode.OFF)
            lightNavigationBarMode(AutoSwitchMode.OFF)
            colorStatusBar(res = R.color.window_background_dark)
            colorNavigationBar(res = R.color.window_background_dark)
            colorWindowBackground(res = R.color.window_background_dark)
            textColorPrimary(res = R.color.text_color_primary_dark)
            textColorSecondary(res = R.color.text_color_secondary_dark)
            textColorPrimaryInverse(res = R.color.text_color_primary)
            textColorSecondaryInverse(res = R.color.text_color_secondary)
        }
    }
}