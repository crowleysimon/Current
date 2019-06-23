package com.crowleysimon.current.ui.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.crowleysimon.current.R
import com.crowleysimon.current.extensions.GlideApp

class RemoteImageView: AppCompatImageView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun load(url: String?) {
        GlideApp.with(context)
            .saturateOnLoad()
            .load(url)
            .placeholder(R.color.image_placeholder)
            .into(this)
    }
}