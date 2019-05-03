package io.hemin.wien.builder

import com.google.common.collect.ImmutableList
import io.hemin.wien.model.Image
import io.hemin.wien.model.Person
import io.hemin.wien.model.Podcast

class PodcastItunesBuilder : Builder<Podcast.Itunes> {

    private var subtitle: String?               = null
    private var summary: String?                = null
    private var image: Image?                   = null
    private var keywords: String?               = null
    private var author: String?                 = null
    private var categories: MutableList<String> = mutableListOf()
    private var explicit: Boolean?              = null
    private var block: Boolean?                 = null
    private var complete: Boolean?              = null
    private var type: String?                   = null
    private var owner: Person?                  = null

    fun subtitle(subtitle: String?) = apply { this.subtitle = subtitle }

    fun summary(summary: String?) = apply { this.summary = summary }

    fun image(image: Image?) = apply { this.image = image }

    fun keywords(keywords: String?) = apply { this.keywords = keywords }

    fun author(author: String?) = apply { this.author = author }

    fun addCategory(category: String?) = apply {
        category?.let { this.categories.add(it) }
    }

    fun explicit(explicit: Boolean?) = apply { this.explicit = explicit }

    fun block(block: Boolean?) = apply { this.block = block }

    fun complete(complete: Boolean?) = apply { this.complete = complete }

    fun type(type: String?) = apply { this.type = type }

    fun owner(owner: Person?) = apply { this.owner = owner }

    override fun build(): Podcast.Itunes? {
        val cats = if (categories.isEmpty()) null else Object()
        return if (Builder.anyNotNull(subtitle, summary, image, keywords, author, cats, explicit, block, complete, type, owner))
            Podcast.Itunes(
                subtitle   = subtitle,
                summary    = summary,
                image      = image,
                keywords   = keywords,
                author     = author,
                categories = ImmutableList.copyOf(categories),
                explicit   = explicit,
                block      = block,
                complete   = complete,
                type       = type,
                owner      = owner
            )
        else
            null
    }

}
