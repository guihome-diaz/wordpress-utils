package eu.daxiongmao.wordrpess.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Generic item.<br>
 *  <code>
 *  &lt;item&gt;<br>
 *      ...
 *  &lt;/item&gt;<br>
 *  </code>
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item implements Serializable {

    /** Type of item: blog post, attachment, notification, etc. Field &lt;wp:post_type&gt; */
    private ItemType itemType;

    /** Name of the item. this is unique, like the slug, and it cannot contains spaces. Field &lt;wp:post_name&gt; */
    @EqualsAndHashCode.Include
    private String name;

    /** Item title. this is what appears on the website, it can contains spaces. Field &lt;title&gt; */
    private String title;

    /** Publication date (GMT). Field &lt;wp:post_date_gmt&gt; */
    private LocalDateTime publicationDateGmt;

    /** Author / Creator. Field &lt;dc:creator&gt; */
    private String author;

    /** Description of the item (optional). Field &lt;description&gt; */
    private String description;

    /** Content as a string. It can be HTML or plain text. It usually include wordpress data. Field &lt;content:encoded&gt; */
    private String content;

    /** Short part of the article to display to tease the blog post. Field &lt;excerpt:encoded&gt; */
    private String excerpt;

    /** Item status: Published, Draft, etc. Field &lt;wp:post_type&gt; */
    private String status;
}
