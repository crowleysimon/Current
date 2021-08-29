package com.crowleysimon.current.ui.reader

import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.applyDimension
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.crowleysimon.current.R
import com.crowleysimon.current.data.ErrorResource
import com.crowleysimon.current.data.LoadingResource
import com.crowleysimon.current.data.Resource
import com.crowleysimon.current.data.SuccessResource
import com.crowleysimon.current.databinding.FragmentReaderBinding
import com.crowleysimon.current.extensions.formatTimeStamp
import com.crowleysimon.current.extensions.loadUrl
import com.crowleysimon.current.ui.SpaceItemDecoration
import com.crowleysimon.current.ui.reader.item.ArticleCardItem
import com.crowleysimon.current.ui.viewBinding
import com.crowleysimon.data.model.Article
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ReaderFragment : Fragment(R.layout.fragment_reader) {

    private val binding by viewBinding(FragmentReaderBinding::bind)
    private val viewModel: ReaderViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.relatedArticlesListView.layoutManager = layoutManager
        viewModel.getArticle(arguments?.getString("articleId", "") ?: "")
        viewModel.getArticles(arguments?.getString("feedId", "") ?: "")
    }

    private fun bindObservers() {
        viewModel.articles.observe(viewLifecycleOwner) { handleRepositoryDataState(it) }
        viewModel.relatedArticles.observe(viewLifecycleOwner) { handleRelatedArticles(it) }
    }

    private fun handleRepositoryDataState(resource: Resource<Article>) = when (resource) {
        is LoadingResource -> setupScreenForLoadingState()
        is SuccessResource -> setupScreenForSuccessState(resource.data)
        is ErrorResource -> setupScreenForErrorState(resource.throwable)
    }

    private fun handleRelatedArticles(resource: Resource<List<ArticleCardItem>>) = when (resource) {
        is LoadingResource -> setupScreenForLoadingState()
        is SuccessResource -> setupArticlesForSuccessState(resource.data)
        is ErrorResource -> setupScreenForErrorState(resource.throwable)
    }

    private fun setupArticlesForSuccessState(data: List<ArticleCardItem>) {
        if (binding.relatedArticlesListView.adapter == null) {
            binding.relatedArticlesListView.addItemDecoration(SpaceItemDecoration(convertDpToPx(16)))
            binding.relatedArticlesListView.adapter =
                GroupAdapter<GroupieViewHolder>().apply { addAll(data) }
        } else {
            (binding.relatedArticlesListView.adapter as GroupAdapter).updateAsync(data)
        }
    }

    private fun convertDpToPx(dp: Int) =
        applyDimension(COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()

    private fun setupScreenForLoadingState() {

    }

    private fun setupScreenForSuccessState(data: Article) {
        binding.articleImageView.loadUrl(data.image)
        binding.articleHeaderView.text = data.title
        binding.articleDateView.text =
            "Published ${DateTime().withMillis(data.pubDate!!).formatTimeStamp(requireContext())}"
        data.author?.let { binding.articleAuthorView.text = "- by $it" }
        binding.articleDescriptionView.text = data.description?.stripHtml()
        binding.articleContinueReading.isVisible = true
        binding.articleContinueReading.setOnClickListener {
            //data.link
            val builder = CustomTabsIntent.Builder()
                .setToolbarColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
                .addDefaultShareMenuItem()
                .setShowTitle(true)

            val customTabsIntent = builder.build()

            // check is chrome available
            //val packageName = customTabHelper.getPackageNameToUse(this, MINDORKS_PUBLICATION)

            /*if (packageName == null) {

            } else {*/
            //customTabsIntent.intent.setPackage(packageName)
            customTabsIntent.launchUrl(requireContext(), Uri.parse(data.link))
            //}
        }

        /*val encodedHtml = Base64.encodeToString(data.description?.toByteArray(), Base64.NO_PADDING)
        readerWebView.loadData(encodedHtml, "text/html", "base64")*/
        binding.scrollView.scrollTo(0, 0)
        if (data.read == false) {
            viewModel.markArticleRead(data.guid)
        }
    }

    private fun setupScreenForErrorState(throwable: Throwable) {
        Timber.e(throwable)
    }

    private fun String.stripHtml(): String = Html.fromHtml(this, FROM_HTML_MODE_LEGACY).toString()
}