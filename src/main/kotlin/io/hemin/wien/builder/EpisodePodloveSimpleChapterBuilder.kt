package io.hemin.wien.builder

import io.hemin.wien.model.Episode

class EpisodePodloveSimpleChapterBuilder : Builder<Episode.Podlove.SimpleChapter> {

    private var start: String? = null
    private var title: String? = null
    private var href: String?  = null
    private var image: String? = null

    /** Set the start. */
    fun start(start: String?) = apply { this.start = start }

    /** Set the title. */
    fun title(title: String?) = apply { this.title = title }

    /** Set the href. */
    fun href(href: String?) = apply { this.href = href }

    /** Set the image. */
    fun image(image: String?) = apply { this.image = image }

    override fun build(): Episode.Podlove.SimpleChapter? {
        return if (anyNotNull(start, title, href, image)) {
            Episode.Podlove.SimpleChapter(
                start = start,
                title = title,
                href  = href,
                image = image
            )
        } else {
            null
        }
    }

}
