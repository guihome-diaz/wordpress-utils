package eu.daxiongmao.wordpress.wxr;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * List of XML tags to process with SAX parser to read/write Wordpress eXchange format Rule (WXR) files.
 * @version 1.0 (based on WP-CLI WXR export). This is based on WXR version 1.2
 * (see <a href="https://github.com/wp-cli/export-command/blob/master/src/WP_Export_WXR_Formatter.php">WP-CLI github</a>
 * @author Guillaume Diaz
 * @since 2020-12
 */
public enum WpCliXmlTag {

    /** WXR document start */
    WEBSITE("channel", null),
    /** Wordpress $metadata['name'] */
    WEBSITE_TITLE("title", WEBSITE.getXmlTag()),
    /** Wordpress $metadata['description'] */
    WEBSITE_DESCRIPTION("description", WEBSITE.getXmlTag()),
    /** Wordpress $metadata['pubDate'] */
    WEBSITE_PUBLICATION_DATE("pubDate", WEBSITE.getXmlTag()),
    /** Wordpress $metadata['url'] */
    WEBSITE_URL("link", WEBSITE.getXmlTag()),
    /** Wordpress $metadata['language'] */
    WEBSITE_LANGUAGE("language", WEBSITE.getXmlTag()),
    /** WXR version */
    WEBSITE_WXR_VERSION("wp:wxr_version", WEBSITE.getXmlTag()),
    /** URL of the wordpress website where the export comes from */
    WEBSITE_WORDPRESS_BASE_SITE_URL("wp:base_site_url", WEBSITE.getXmlTag()),
    /** URL of the wordpress website where the export comes from */
    WEBSITE_WORDPRESS_BASE_BLOG_URL("wp:base_blog_url", WEBSITE.getXmlTag()),

    AUTHOR("wp:author", WEBSITE.getXmlTag()),
    AUTHOR_ID("wp:author_id", AUTHOR.getXmlTag()),
    AUTHOR_LOGIN("wp:author_login", AUTHOR.getXmlTag()),
    AUTHOR_EMAIL("wp:author_email", AUTHOR.getXmlTag()),
    AUTHOR_DISPLAY_NAME("wp:author_display_name", AUTHOR.getXmlTag()),
    AUTHOR_FIRST_NAME("wp:author_first_name", AUTHOR.getXmlTag()),
    AUTHOR_LAST_NAME("wp:author_last_name", AUTHOR.getXmlTag()),

    CATEGORY("wp:category", WEBSITE.getXmlTag()),
    CATEGORY_ID("wp:term_id", CATEGORY.getXmlTag()),
    CATEGORY_SLUG("wp:category_nicename", CATEGORY.getXmlTag()),
    CATEGORY_NAME("wp:cat_name", CATEGORY.getXmlTag()),
    CATEGORY_PARENT("wp:category_parent", CATEGORY.getXmlTag()),

    TAG("wp:tag", WEBSITE.getXmlTag()),
    TAG_ID("wp:term_id", TAG.xmlTag),
    TAG_SLUG("wp:tag_slug", TAG.getXmlTag()),
    TAG_NAME("wp:tag_name", TAG.getXmlTag()),

    ITEM("item", WEBSITE.getXmlTag()),
    ITEM_TITLE("title", ITEM.getXmlTag()),
    ITEM_AUTHOR("dc:creator", ITEM.getXmlTag()),
    ITEM_DESCRIPTION("description", ITEM.getXmlTag()),
    ITEM_CONTENT("content:encoded", ITEM.getXmlTag()),
    ITEM_EXCERPT("excerpt:encoded", ITEM.getXmlTag()),
    ITEM_PUBLICATION_DATE_GMT("wp:post_date_gmt", ITEM.getXmlTag()),
    ITEM_NAME("wp:post_name", ITEM.getXmlTag()),
    ITEM_STATUS("wp:status", ITEM.getXmlTag()),
    ITEM_TYPE("wp:post_type", ITEM.getXmlTag()),
    ITEM_ATTACHMENT_URL("wp:attachment_url", ITEM.getXmlTag()),
    ITEM_CATEGORY("category", ITEM.getXmlTag()),

    ITEM_POST_META("wp:postmeta", ITEM.getXmlTag()),
    ITEM_POST_META_KEY("wp:meta_key", ITEM_POST_META.getXmlTag()),
    ITEM_POST_META_VALUE("wp:meta_value", ITEM_POST_META.getXmlTag()),

    ITEM_COMMENT("wp:comment", ITEM.getXmlTag()),
    ITEM_COMMENT_ID("wp:comment_id", ITEM_COMMENT.getXmlTag()),
    ITEM_COMMENT_AUTHOR("wp:comment_author", ITEM_COMMENT.getXmlTag()),
    ITEM_COMMENT_AUTHOR_EMAIL("wp:comment_author_email", ITEM_COMMENT.getXmlTag()),
    ITEM_COMMENT_PUBLICATION_DATE_GMT("wp:comment_date_gmt", ITEM_COMMENT.getXmlTag()),
    ITEM_COMMENT_CONTENT("wp:comment_content", ITEM_COMMENT.getXmlTag())

    ;

    /** Real XML tag, as it appears in the file. */
    private String xmlTag;

    /** Parent tag, if applicable. */
    private String parent;

    /**
     * <p>Dictionary of XML tags and corresponding Enum.</p>
     * <ul>
     *     <li>key: parentTag/xmlTag</li>
     *     <li>value: enum</li>
     * </ul>
     * <p>Performance trick:
     * Instead of doing O(n) complexity everytime with a for loop,
     * let's load the values at startup in map and do O(1) for every search.
     * </p>
     */
    private static Map<String, WpCliXmlTag> tagsMapping = new HashMap<>();
    static {
        Arrays.stream(WpCliXmlTag.values()).forEach(tag -> tagsMapping.put((tag.getParent() != null ? tag.getParent() + "/" : "") + tag.getXmlTag(), tag));
    }

    WpCliXmlTag(final String xmlTag, final String parent) {
        this.xmlTag = xmlTag;
        this.parent = parent;
    }

    private String getParent() {
        return parent;
    }

    public String getXmlTag() {
        return xmlTag;
    }

    /**
     * To retrieve the Enum tag corresponding to a particular XML value
     * @param xmlTag search XML tag (file value)
     * @param parent (optional) parent tag, if applicable
     * @return corresponding enum tag or NULL
     */
    public static WpCliXmlTag getTag(final String xmlTag, final WpCliXmlTag parent) {
        return tagsMapping.get((parent != null ? parent.getXmlTag() + "/" : "") + xmlTag);
    }
}
