package com.crowleysimon.current.extensions

import android.widget.ImageView
import coil.load
import coil.transform.Transformation
import com.crowleysimon.current.R

fun ImageView.loadUrl(url: String?, transformations: List<Transformation> = emptyList()) {
    this.load(url) {
        placeholder(R.color.image_placeholder)
        if (transformations.isNotEmpty()) {
            transformations(transformations)
        }
        crossfade(true)
    }
}