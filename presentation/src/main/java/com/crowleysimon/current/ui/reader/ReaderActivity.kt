package com.crowleysimon.current.ui.reader

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.crowleysimon.current.ui.feed.FeedAdapter
import com.crowleysimon.current.ui.feed.FeedViewModel
import com.crowleysimon.domain.model.Article
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_feed.*
import javax.inject.Inject

class ReaderActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var readerViewModel: ReaderViewModel

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

        setContentView(R.layout.activity_reader)
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
        readerViewModel = ViewModelProviders.of(this, viewModelFactory).get(ReaderViewModel::class.java)
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
        Toast.makeText(this, throwable.localizedMessage, Toast.LENGTH_LONG).show()
    }

    private fun setupViews() {

    }

}