package com.crowleysimon.remote

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class RssItemConverterFactory

private constructor() : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *> = RssResponseConverter()

    companion object {
        fun create(): RssItemConverterFactory = RssItemConverterFactory()
    }
}