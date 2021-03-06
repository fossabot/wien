package io.hemin.wien.parser

import io.hemin.wien.builder.EpisodeBuilder
import io.hemin.wien.builder.PodcastBuilder
import io.hemin.wien.model.Episode
import io.hemin.wien.model.Image
import io.hemin.wien.model.Podcast
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.w3c.dom.Node
import java.util.*

/** Provides unit tests for [RssParser]. */
class RssParserTest : NamespaceParserTest() {

    override val parser = RssParser()

    val channel: Node? = nodeFromResource("channel", "/xml/channel.xml")
    val item: Node? = nodeFromResource("item", "/xml/item.xml")

    val expectedDate: Date? = DateParser.parse("Fri, 16 Mar 2018 22:49:08 +0000")
    val expectedEnclosure = Episode.Enclosure(
        url    = "http://example.org/episode1.m4a",
        length = 78589133,
        type   = "audio/mp4")
    val expectedGuid = Episode.Guid(
        textContent = "1fa609024fdf097",
        isPermalink = true
    )
    val expectedImage = Image(
        url         = "http://example.org/podcast-cover.jpg",
        title       = "Lorem Ipsum",
        link        = "http://example.org",
        width       = 600,
        height      = 600,
        description = "Lorem Ipsum"
    )

    @Test
    fun testParseChannel() {
        channel?.let {
            val builder = PodcastBuilder()
            parse(builder, it)
            val p: Podcast = builder.build()

            assertEquals("Lorem Ipsum", p.title)
            assertEquals("http://example.org", p.link)
            assertEquals("Lorem Ipsum", p.description)
            assertEquals(expectedDate, p.pubDate)
            assertEquals(expectedDate, p.lastBuildDate)
            assertEquals("de-DE", p.language)
            assertEquals("Lorem Ipsum", p.generator)
            assertEquals("Lorem Ipsum", p.copyright)
            assertEquals("Lorem Ipsum", p.docs)
            assertEquals("editor@example.org", p.managingEditor)
            assertEquals("webmaster@example.org", p.webMaster)
            assertEquals(expectedImage, p.image)
        } ?: run {
            fail("channel not found")
        }
    }

    @Test
    fun testChannelMissingElements() {
        val incompleteChannel: Node? = nodeFromResource("channel", "/xml/channel-incomplete.xml")
        incompleteChannel?.let {
            val builder = PodcastBuilder()
            parse(builder, it)
            val p: Podcast = builder.build()

            assertNull(p.image)
        } ?: run {
            fail("channel not found")
        }
    }

    @Test
    fun testParseItem() {
        item?.let {
            val builder = EpisodeBuilder()
            parse(builder, it)
            val e: Episode = builder.build()

            assertEquals("Lorem Ipsum", e.title)
            assertEquals("http://example.org/episode1", e.link)
            assertEquals("Lorem Ipsum", e.description)
            assertEquals("author@example.org", e.author)
            assertEquals(listOf("category1","category2"), e.categories)
            assertEquals("http://example.org/episode1/comments", e.comments)
            assertEquals(expectedEnclosure, e.enclosure)
            assertEquals(expectedGuid, e.guid)
            assertEquals(expectedDate, e.pubDate)
            assertEquals("http://example.org/rss", e.source)
        } ?: run {
            fail("item not found")
        }
    }

    @Test
    fun testItemMissingElements() {
        val incompleteItem: Node? = nodeFromResource("item", "/xml/item-incomplete.xml")
        incompleteItem?.let {
            val builder = EpisodeBuilder()
            parse(builder, it)
            val e: Episode = builder.build()

            assertNull(e.enclosure)
            assertNull(e.guid)
        } ?: run {
            fail("item not found")
        }
    }

}
