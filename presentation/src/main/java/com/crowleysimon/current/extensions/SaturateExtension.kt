package com.crowleysimon.current.extensions

import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideType
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@GlideExtension
object SaturateExtension {

    @GlideType(Drawable::class)
    @JvmStatic
    fun saturateOnLoad(requestBuilder: RequestBuilder<Drawable>): RequestBuilder<Drawable> {
        return requestBuilder.transition(DrawableTransitionOptions.with(SaturationTransitionFactory()))
    }
}
