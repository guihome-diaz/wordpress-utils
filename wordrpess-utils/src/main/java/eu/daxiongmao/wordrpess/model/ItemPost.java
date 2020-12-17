package eu.daxiongmao.wordrpess.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

/**
 * Post (= Item with a particular post type 'Item').<br>
 *     <code>
 *         &lt;item&gt;<br>
 *             ..<br>
 *         &nbsp;&nbsp;&lt;wp:post_type&gt;post&lt;/wp:post_type&gt;<br>
 *             ..<br>
 *         &lt;/item&gt;<br>
 *     </code>
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ItemPost extends Item {

    /** Optional: categories related to that item.<br>
     * This contains the list of categories' slugs.<br>
     * &lt;category domain="category" nicename="slug"><![CDATA[name]]></category>
     */
    private final Set<String> categories = new HashSet<>();

    /** Optional: tags related to that item.<br>
     * This contains the list of tags' slugs.<br>
     * &lt;category domain="post_tag" nicename="slug"><![CDATA[name]]></category>
     */
    private final Set<String> tags = new HashSet<>();

    /**
     * Optional: posts' comments. Field: &lt;wp:comment&gt;
     */
    private final Set<Comment> comments = new HashSet<>();

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
    private final Set<String> enclosures = new HashSet<>();

}
