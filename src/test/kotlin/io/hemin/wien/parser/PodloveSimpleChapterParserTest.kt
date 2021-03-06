package io.hemin.wien.parser

import io.hemin.wien.builder.EpisodeBuilder
import io.hemin.wien.builder.EpisodePodloveSimpleChapterBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.w3c.dom.Node

/** Provides unit tests for [PodloveSimpleChapterParser]. */
class PodloveSimpleChapterParserTest : NamespaceParserTest() {

    override val parser = PodloveSimpleChapterParser()

    val item: Node? = nodeFromResource("item", "/xml/item.xml")

    val expectedSimpleChapter = EpisodePodloveSimpleChapterBuilder()
        .start("00:00:00.000")
        .title("Lorem Ipsum")
        .href("http://example.org")
        .image("http://example.org/cover")
        .build()

    @Test
    fun testParseItemPodloveSimpleChapters() {
        item?.let {
            val builder = EpisodeBuilder()
            parse(builder, it)

            builder.build().podlove?.let {
                assertEquals(3, it.simpleChapters.size)
                assertTrue(it.simpleChapters.contains(expectedSimpleChapter))
            } ?: run {
                fail("Episode Podlove Simple Chapter data not extracted")
            }
        } ?: run {
            fail("item not found")
        }
    }

}
