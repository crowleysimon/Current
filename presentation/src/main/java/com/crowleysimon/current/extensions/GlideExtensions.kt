package com.crowleysimon.current.extensions/*
import android.graphics.drawable.Drawable
import android.annotation.SuppressLint
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideType
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.crowleysimon.current.extensions.SaturationTransitionFactory

@SuppressLint("CheckResult")
@GlideExtension
object GlideExtensions {

    @JvmStatic
    @GlideType(Drawable::class)
    fun saturateOnLoad(requestBuilder: RequestBuilder<Drawable>): RequestBuilder<Drawable> {
        return requestBuilder.transition(DrawableTransitionOptions.with(SaturationTransitionFactory()))
    }
}*/
