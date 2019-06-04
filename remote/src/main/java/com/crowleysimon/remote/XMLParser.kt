package com.crowleysimon.remote

import com.crowleysimon.remote.model.RssItemModel
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler

internal class XMLParser : DefaultHandler() {

    private var elementOn = false
    private var parsingElement: String = EMPTY_STRING

    private var elementValue: String? = null

    private var title = EMPTY_STRING
    private var link: String? = null
    private var image: String? = null
    private var date: String? = null
    private var description: String? = null
    private var author: String? = null
    private var category: String? = null
    private var channel: String? = null
    private var copyright: String? = null
    private var generator: String? = null
    private var guid: String? = null
    private var lastBuildDate: String? = null
    private var managingEditor: String? = null
    private var ttl: String? = null

    private var rssItem: RssItemModel? = null
    val items = mutableListOf<RssItemModel>()

    @Throws(SAXException::class)
    override fun startElement(uri: String, localName: String, qName: String, attributes: Attributes?) {
        elementOn = true
        when (localName.toLowerCase()) {
            ITEM,
            ENTRY -> rssItem = RssItemModel()
            TITLE -> if (!qName.contains(MEDIA)) {
                parsingElement = TITLE
                title = EMPTY_STRING
            }
            DESCRIPTION -> {
                parsingElement = DESCRIPTION
                description = EMPTY_STRING
            }
            LINK -> if (qName != ATOM_LINK) {
                parsingElement = LINK
                link = EMPTY_STRING
            }
            CREATOR, AUTHOR -> {
                parsingElement = AUTHOR
                author = EMPTY_STRING
            }
            CATEGORY -> {
                parsingElement = CATEGORY
                category = EMPTY_STRING
            }
            CHANNEL -> {
                parsingElement = CHANNEL
                channel = EMPTY_STRING
            }
            COPYRIGHT -> {
                parsingElement = COPYRIGHT
                copyright = EMPTY_STRING
            }
            GENERATOR -> {
                parsingElement = GENERATOR
                generator = EMPTY_STRING
            }
            GUID -> {
                parsingElement = GUID
                guid = EMPTY_STRING
            }
            LAST_BUILD_DATE -> {
                parsingElement = LAST_BUILD_DATE
                lastBuildDate = EMPTY_STRING
            }
            MANAGING_EDITOR -> {
                parsingElement = MANAGING_EDITOR
                managingEditor = EMPTY_STRING
            }
            TTL -> {
                parsingElement = TTL
                ttl = EMPTY_STRING
            }
        }

        if (attributes != null) {
            val url = attributes.getValue(URL)
            if (url != null && !url.isEmpty()) {
                image = url
            }
        }
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, localName: String, qName: String) {
        elementOn = false
        if (rssItem != null) {
            when (localName.toLowerCase()) {
                ENTRY,
                ITEM -> {
                    rssItem = RssItemModel(
                        author = author,
                        category = category,
                        channel = channel,
                        copyright = copyright,
                        description = description,
                        generator = generator,
                        guid = guid,
                        image = image.takeIf { !it.isNullOrBlank() } ?: getImageSourceFromDescription(description),
                        lastBuildDate = lastBuildDate,
                        link = link,
                        managingEditor = managingEditor,
                        pubDate = date,
                        title = title.trim { it <= ' ' },
                        ttl = ttl
                    )
                    rssItem?.let { items.add(it) }
                    link = EMPTY_STRING
                    image = EMPTY_STRING
                    date = EMPTY_STRING
                    author = EMPTY_STRING
                    category = EMPTY_STRING
                    channel = EMPTY_STRING
                    generator = EMPTY_STRING
                    copyright = EMPTY_STRING
                    guid = EMPTY_STRING
                    lastBuildDate = EMPTY_STRING
                    managingEditor = EMPTY_STRING
                    ttl = EMPTY_STRING
                }
                TITLE -> if (!qName.contains(MEDIA)) {
                    parsingElement = EMPTY_STRING
                    elementValue = EMPTY_STRING
                    title = removeNewLine(title)
                }
                LINK -> if (elementValue?.isNotEmpty() == true) {
                    parsingElement = EMPTY_STRING
                    elementValue = EMPTY_STRING
                    link = removeNewLine(link)
                }
                IMAGE, URL -> if (elementValue != null && elementValue?.isNotEmpty() == true) {
                    image = elementValue
                }
                PUBLISH_DATE -> {
                    parsingElement = EMPTY_STRING
                    date = elementValue
                }
                DESCRIPTION,
                CREATOR,
                AUTHOR,
                CATEGORY,
                CHANNEL,
                COPYRIGHT,
                GENERATOR,
                GUID,
                LAST_BUILD_DATE,
                MANAGING_EDITOR,
                TTL -> {
                    parsingElement = EMPTY_STRING
                    elementValue = EMPTY_STRING
                }
            }
        }
    }

    @Throws(SAXException::class)
    override fun characters(ch: CharArray, start: Int, length: Int) {
        val buff = String(ch, start, length)
        if (elementOn) {
            if (buff.length > 2) {
                elementValue = buff
                elementOn = false
            }
        }
        when (parsingElement) {
            TITLE -> title += buff
            DESCRIPTION -> description += buff
            LINK -> link += buff
            CREATOR, AUTHOR -> author += buff
            CATEGORY -> category += buff
            CHANNEL -> channel += buff
            COPYRIGHT -> copyright += buff
            GENERATOR -> generator += buff
            GUID -> guid += buff
            LAST_BUILD_DATE -> lastBuildDate += buff
            MANAGING_EDITOR -> managingEditor += buff
            TTL -> ttl += buff
        }
    }

    /**
     * Image is parsed from description if image is null
     *
     * @param description description
     * @return Image url
     */
    private fun getImageSourceFromDescription(description: String?): String? {
        if (description?.contains("<img") == true && description.contains("src")) {
            val parts = description.split("src=\"".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (parts.size == 2 && parts[1].isNotEmpty()) {
                var src = parts[1].substring(0, parts[1].indexOf("\""))
                val srcParts = src.split("http".toRegex())
                    .dropLastWhile { it.isEmpty() }
                    .toTypedArray() // can be removed
                if (srcParts.size > 2) {
                    src = "http" + srcParts[2]
                }
                return src
            }
        }
        return null
    }

    private fun removeNewLine(s: String?): String = s?.replace("\n", "") ?: EMPTY_STRING

    companion object {
        private const val EMPTY_STRING = ""
        private const val ITEM = "item"
        private const val ENTRY = "entry"
        private const val TITLE = "title"
        private const val MEDIA = "media"
        private const val DESCRIPTION = "description"
        private const val LINK = "link"
        private const val ATOM_LINK = "atom:link"
        private const val URL = "url"
        private const val IMAGE = "image"
        private const val PUBLISH_DATE = "pubdate"
        private const val AUTHOR = "author"
        private const val CREATOR = "dc:creator"
        private const val CATEGORY = "category"
        private const val CHANNEL = "channel"
        private const val COPYRIGHT = "copyright"
        private const val GENERATOR = "generator"
        private const val GUID = "guid"
        private const val LAST_BUILD_DATE = "lastBuildDate"
        private const val MANAGING_EDITOR = "managingEditor"
        private const val TTL = "ttl"
    }
}