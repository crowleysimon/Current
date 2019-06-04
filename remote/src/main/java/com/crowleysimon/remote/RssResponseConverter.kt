package com.crowleysimon.remote

import com.crowleysimon.remote.model.RssFeedModel
import org.xml.sax.InputSource
import javax.xml.parsers.SAXParserFactory

import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.InputStream

internal class RssResponseConverter : Converter<ResponseBody, RssFeedModel> {

    override fun convert(value: ResponseBody): RssFeedModel {
        return try {
            RssFeedModel(RssParser().parse(value.byteStream()))
        } catch (e: Exception) {
            e.printStackTrace()
            RssFeedModel(emptyList())
        }
    }
}