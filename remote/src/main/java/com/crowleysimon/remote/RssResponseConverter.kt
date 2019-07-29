package com.crowleysimon.remote

import com.crowleysimon.remote.model.RssFeedModel
import okhttp3.ResponseBody
import retrofit2.Converter

internal class RssResponseConverter : Converter<ResponseBody, RssFeedModel> {

    override fun convert(value: ResponseBody): RssFeedModel {
        return try {
            RssParser().parse(value.byteStream())
        } catch (e: Exception) {
            e.printStackTrace()
            RssFeedModel(null, emptyList())
        }
    }
}