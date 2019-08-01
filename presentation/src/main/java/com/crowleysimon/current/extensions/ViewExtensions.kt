package com.crowleysimon.current.extensions

import android.widget.ImageView
import com.crowleysimon.current.R
import com.crowleysimon.current.extensions.glide.GlideApp

fun ImageView.load(url: String?) {
    GlideApp.with(context)
        .saturateOnLoad()
        .load(url)
        .placeholder(R.color.image_placeholder)
        .into(this)
}