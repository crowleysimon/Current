package com.crowleysimon.current.extensions

import android.widget.ImageView
import com.crowleysimon.current.R

fun ImageView.load(url: String?) {
    GlideApp.with(context)
        .saturateOnLoad()
        .load(url)
        .placeholder(R.color.image_placeholder)
        .into(this)
}