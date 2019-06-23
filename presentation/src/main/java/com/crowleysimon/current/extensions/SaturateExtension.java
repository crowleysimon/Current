package com.crowleysimon.current.extensions;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

@GlideExtension
public class SaturateExtension {

    private SaturateExtension() { }

    @NonNull
    @GlideType(Drawable.class)
    public static RequestBuilder<Drawable> saturateOnLoad(RequestBuilder<Drawable> requestBuilder) {
        return requestBuilder.transition(DrawableTransitionOptions.with(new SaturationTransitionFactory()));
    }
}
