package io.hemin.wien.parser

import io.hemin.wien.model.Episode
import io.hemin.wien.model.Podcast
import org.w3c.dom.Node

class RssParser : NamespaceParser {

    companion object {
        val NAMESPACE: String? = null
    }

    override val namespace: String? = NAMESPACE

    override fun parse(podcast: Podcast.Builder, node: Node) {
        // TODO write a logger warning
        println("WARNING: parse(Podcast.Builder, Node) was called. Nested Episodes will have only RSS namespace metadata. Use parse(Podcast.Builder, Node, (Node)->Episode) to enable parsing of additional XML namespaces")
        parse(podcast, node, fun(n: Node): Episode {
            val e: Episode.Builder = Episode.Builder()
            parse(e, n)
            return e.build()
        })
    }

    override fun parse(episode: Episode.Builder, node: Node) {
        when (node.localName) {
            "title"       -> episode.title(toText(node))
            "link"        -> episode.link(toText(node))
            "description" -> episode.description(toText(node))
            "author"      -> episode.author(toText(node))
            "category"    -> episode.addCategory(toText(node))
            "comments"    -> episode.comments(toText(node))
            "enclosure"   -> episode.enclosure(toEnclosure(node))
            "guid"        -> episode.guid(toText(node))
            "pubDate"     -> episode.pubDate(toDate(node))
            "source"      -> episode.source(toText(node))
        }
    }

    fun parse(podcast: Podcast.Builder, node: Node, toEpisode: (Node) -> Episode) {
        when (node.localName) {
            "title"          -> podcast.title(toText(node))
            "link"           -> podcast.link(toText(node))
            "description"    -> podcast.description(toText(node))
            "pubDate"        -> podcast.pubDate(toDate(node))
            "lastBuildDate"  -> podcast.lastBuildDate(toDate(node))
            "language"       -> podcast.language(toText(node))
            "generator"      -> podcast.generator(toText(node))
            "copyright"      -> podcast.copyright(toText(node))
            "docs"           -> podcast.docs(toText(node))
            "managingEditor" -> podcast.managingEditor(toText(node))
            "webMaster"      -> podcast.webMaster(toText(node))
            "item"           -> podcast.addEpisode(toEpisode(node))
        }
    }

}