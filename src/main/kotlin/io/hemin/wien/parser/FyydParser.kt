package io.hemin.wien.parser

import io.hemin.wien.builder.EpisodeBuilder
import io.hemin.wien.builder.PodcastBuilder
import org.w3c.dom.Node

/**
 * Parser implementation for the Fyyd namespace.
 *
 * The namespace URI is: `https://fyyd.de/fyyd-ns/`
 */
class FyydParser : NamespaceParser() {

    override val namespaceURI: String? = "https://fyyd.de/fyyd-ns/"

    override fun parseChannel(builder: PodcastBuilder, node: Node) {
        when (node.localName) {
            "verify" -> builder.fyyd.verify(toText(node))
        }
    }

    /** This module does not set any data in the [EpisodeBuilder]. */
    override fun parseItem(builder: EpisodeBuilder, node: Node) { }

}
