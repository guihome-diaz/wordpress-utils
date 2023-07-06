package eu.daxiongmao.wordpress.wxr.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private ZonedDateTime publicationDateGmt;

    /** Author / Creator. Field &lt;dc:creator&gt; */
    private String author;

    /** Description of the item (optional). Field &lt;description&gt; */
    private String description;

    /** Content as a string. It can be HTML or plain text. It usually include WordPress data. Field &lt;content:encoded&gt; */
    @ToString.Exclude
    private String content;

    /** Short part of the article to display to tease the blog post. Field &lt;excerpt:encoded&gt; */
    private String excerpt;

    /** Item status: Published, Draft, etc. Field &lt;wp:post_type&gt; */
    private String status;

    // ******************* Specific POST items ******************

    /** Optional: categories related to that item.<br>
     * This contains the list of categories' slugs.<br>
     * &lt;category domain="category" nicename="slug"><![CDATA[name]]></category>
     */
    @ToString.Exclude
    private final Set<String> categories = new HashSet<>();

    /** Optional: tags related to that item.<br>
     * This contains the list of tags' slugs.<br>
     * &lt;category domain="post_tag" nicename="slug"><![CDATA[name]]></category>
     */
    @ToString.Exclude
    private final Set<String> tags = new HashSet<>();

    /**
     * Optional: post's comments. Fields: &lt;wp:comment&gt;
     */
    @ToString.Exclude
    private final Set<Comment> comments = new HashSet<>();

    /**
     * Optional: post's metadata. Fields: &lt;wp:postmeta&gt;
     */
    private final Set<PostMeta> metadata = new HashSet<>();

    /**
     * <p>
     * Set of Enclosures (links) to article's media. <br>
     * <code>
     * &lt;wp:postmeta&gt;<br>
     * &nbsp;&nbsp;&lt;wp:meta_key&gt;enclosure&lt;/wp:meta_key&gt;<br>
     * &nbsp;&nbsp;&lt;wp:meta_value&gt;__url__&lt;/wp:meta_value&gt;<br>
     * &lt;/wp:postmeta&gt;<br>
     * </code>
     * </p>
     * <p><br>
     * "Enclosures" are links in a post to something like an audio or video file.
     * WordPress finds these based on the MIME type of the files being linked to, then saves extra metadata about them.
     * This metadata is used in the RSS feeds, to create special tags to connect these files to the post.
     * </p>
     * <p><br>
     * This is how podcasts work, for example.
     * If you put a link to an MP3 in a post, then an enclosure will be created for that link, and the feed will have
     * the enclosure, and podcast readers like iTunes can then use that to be able to download the MP3 directly
     * from the RSS feed.
     * </p>
     * <p><br>
     * <code>_encloseme</code> is just special metadata saying that the post hasn't been processed by the enclosure
     * process yet. When you create or update a published post, that gets auto-added so that the post will
     * be processed by the enclosure creator.
     * </p>
     * <p>Source: https://wordpress.stackexchange.com/questions/20904/the-encloseme-meta-key-conundrum</p>
     */
    @ToString.Exclude
    private final Set<String> enclosures = new HashSet<>();

    // ******************* Specific ATTACHEMENT items ******************

    /** Attachment URL. Field &lt;wp:attachment_url&gt; */
    private String attachmentUrl;

}
