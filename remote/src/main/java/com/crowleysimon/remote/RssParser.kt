package com.crowleysimon.remote

import android.util.Xml
import com.crowleysimon.remote.model.RssFeedModel
import com.crowleysimon.remote.model.RssItemModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import timber.log.Timber
import java.io.IOException
import java.io.InputStream

class RssParser {

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): RssFeedModel {
        inputStream.use { stream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(stream, null)
            parser.nextTag()
            return readXml(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readXml(parser: XmlPullParser): RssFeedModel {
        return when (parser.name) {
            FEED -> readFeed(parser)
            RSS -> readRss(parser)
            else -> RssFeedModel(null, mutableListOf())
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): RssFeedModel {
        val entries = mutableListOf<RssItemModel>()
        var title: String? = null
        parser.require(XmlPullParser.START_TAG, ns, FEED)
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                TITLE -> title = readSimpleData(parser)
                ENTRY -> entries.add(readEntry(parser))
                else -> skip(parser)
            }
        }
        return RssFeedModel(title, entries)
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readRss(parser: XmlPullParser): RssFeedModel {
        val entries = mutableListOf<RssItemModel>()
        var title: String? = null

        parser.require(XmlPullParser.START_TAG, ns, RSS)
        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (parser.name) {
                TITLE -> title = readSimpleData(parser)
                ITEM -> entries.add(readEntry(parser))
                else -> Timber.w("${parser.name} == name")
            }
            eventType = parser.next()
        }
        return RssFeedModel(title, entries)
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
    // to their respective "read" methods for processing. Otherwise, skips the tag.
    @Throws(XmlPullParserException::class, IOException::class)
    private fun readEntry(parser: XmlPullParser): RssItemModel {
        var author: String? = null
        var category: String? = null
        var channel: String? = null
        var copyright: String? = null
        var description: String? = null
        var generator: String? = null
        var guid: String? = null
        var image: String? = null
        var item: String? = null
        var lastBuildDate: String? = null
        var link: String? = null
        var managingEditor: String? = null
        var pubDate: String? = null
        var title: String? = null
        var ttl: String? = null
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                TITLE -> title = readSimpleData(parser)
                LINK -> link = readLink(parser)
                ID,
                GUID -> guid = readSimpleData(parser)
                PUBLISHED,
                PUB_DATE -> pubDate = readSimpleData(parser)
                LAST_BUILD_DATE,
                UPDATED -> lastBuildDate = readSimpleData(parser)
                DESCRIPTION,
                SUMMARY,
                CONTENT -> {
                    val summaryInfo = readSummary(parser)
                    description = summaryInfo.first
                    image = summaryInfo.second
                }

                AUTHOR,
                CATEGORY,
                CHANNEL,
                FEED,
                COPYRIGHT,
                RIGHTS,
                SUBTITLE,
                GENERATOR,
                IMAGE,
                LOGO,
                MANAGING_EDITOR,
                CONTRIBUTOR,
                TTL -> skip(parser)
                else -> skip(parser)
            }
        }
        return RssItemModel(
            author = author,
            category = category,
            channel = channel,
            copyright = copyright,
            description = description,
            generator = generator,
            guid = guid,
            image = image,
            item = item,
            lastBuildDate = lastBuildDate,
            link = link,
            managingEditor = managingEditor,
            pubDate = pubDate,
            title = title,
            ttl = ttl
        )
    }

    private fun readSimpleData(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, parser.name)
        val data = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, parser.name)
        return data
    }

    // Processes link tags in the feed.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readLink(parser: XmlPullParser): String {
        var link = ""
        parser.require(XmlPullParser.START_TAG, ns, LINK)
        val tag = parser.name
        val relType = parser.getAttributeValue(null, "rel")
        if (tag == LINK) {
            if (relType == "alternate") {
                link = parser.getAttributeValue(null, "href")
                parser.nextTag()
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, LINK)
        return link
    }

    // Processes summary tags in the feed.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readSummary(parser: XmlPullParser): Pair<String?, String?> {
        val summary = readSimpleData(parser)
        val htmlDocument = Jsoup.parse(summary)
        val imgElement: Element? = htmlDocument.select("img").first()
        val imgUrl = imgElement?.absUrl("src")
        return Pair(summary, imgUrl)
    }

    // For the tags title and summary, extracts their text values.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }

    companion object {
        const val AUTHOR = "author"
        const val CATEGORY = "category"
        const val CHANNEL = "channel"
        const val FEED = "feed"
        const val RSS = "rss"
        const val COPYRIGHT = "copyright"
        const val RIGHTS = "rights"
        const val SUBTITLE = "subtitle"
        const val DESCRIPTION = "description"
        const val SUMMARY = "summary"
        const val CONTENT = "content"
        const val GENERATOR = "generator"
        const val GUID = "guid"
        const val ID = "id"
        const val IMAGE = "image"
        const val LOGO = "logo"
        const val LAST_BUILD_DATE = "lastBuildDate"
        const val UPDATED = "updated"
        const val LINK = "link"
        const val MANAGING_EDITOR = "managingEditor"
        const val CONTRIBUTOR = "contributor"
        const val PUB_DATE = "pubDate"
        const val PUBLISHED = "published"
        const val TITLE = "title"
        const val TTL = "ttl"
        const val ENTRY = "entry"
        const val ITEM = "item"
        private val ns: String? = null
    }

}