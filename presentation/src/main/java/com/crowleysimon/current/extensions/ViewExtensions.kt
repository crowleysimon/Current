package com.crowleysimon.current.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.crowleysimon.current.R

fun ImageView.loadUrl(url: String?) {
    Glide.with(context)
        //.saturateOnLoad()
        .load(url)
        .placeholder(R.color.image_placeholder)
        .into(this)
}