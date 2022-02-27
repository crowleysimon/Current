package com.crowleysimon.current.ui.reader.item

import android.view.View
import com.crowleysimon.current.R
import com.crowleysimon.current.data.ViewBindingItem
import com.crowleysimon.current.databinding.ItemCardArticleBinding
import com.crowleysimon.current.extensions.loadUrl
import com.crowleysimon.data.model.Article

class ArticleCardItem(
    private val guid: String,
    private val image: String,
    private val title: String,
    val onItemClick: (guid: String) -> Unit
) : ViewBindingItem<ItemCardArticleBinding>() {

    override fun inflate(itemView: View): ItemCardArticleBinding = ItemCardArticleBinding.bind(itemView)

    override fun getLayout(): Int = R.layout.item_card_article

    override fun bind(viewBinding: ItemCardArticleBinding, position: Int) {
        viewBinding.apply {
            articleCardImage.loadUrl(image)
            articleCardHeader.text = title
            root.setOnClickListener { onItemClick(guid) }
        }
    }
}

fun Article.toCardItem(onItemClick: (guid: String) -> Unit): ArticleCardItem {
    return ArticleCardItem(
        this.guid,
        this.image ?: "",
        this.title ?: "",
        onItemClick
    )
}