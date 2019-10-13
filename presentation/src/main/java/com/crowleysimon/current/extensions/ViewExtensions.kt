package com.crowleysimon.current.extensions

import android.widget.ImageView
import coil.api.load
import com.crowleysimon.current.R

fun ImageView.loadUrl(url: String?) {
    this.load(url) {
        placeholder(R.color.image_placeholder)
        crossfade(true)
    }
}