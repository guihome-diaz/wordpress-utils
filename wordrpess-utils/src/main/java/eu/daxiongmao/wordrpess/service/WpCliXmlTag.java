package eu.daxiongmao.wordrpess.service;

import eu.daxiongmao.wordrpess.model.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * List of XML tags to process with SAX parser.
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
public enum WpCliXmlTag {

    WEBSITE_TITLE("title", Website.class, null),
    WEBSITE_DESCRIPTION("description", Website.class, null),
    WEBSITE_ROOT_URL("link", Website.class, null),

    AUTHOR("wp:author", Author.class, null),
    AUTHOR_LOGIN("wp:author_login", Author.class, AUTHOR.getXmlTag()),
    AUTHOR_EMAIL("wp:author_email", Author.class, AUTHOR.getXmlTag()),
    AUTHOR_DISPLAY_NAME("wp:author_display_name", Author.class, AUTHOR.getXmlTag()),
    AUTHOR_FIRST_NAME("wp:author_first_name", Author.class, AUTHOR.getXmlTag()),
    AUTHOR_LAST_NAME("wp:author_last_name", Author.class, AUTHOR.getXmlTag()),

    CATEGORY("wp:category", Category.class, null),
    CATEGORY_SLUG("wp:category_nicename", Category.class, CATEGORY.getXmlTag()),
    CATEGORY_NAME("wp:cat_name", Category.class, CATEGORY.getXmlTag()),
    CATEGORY_PARENT("wp:category_parent", Category.class, CATEGORY.getXmlTag()),

    ITEM("item", Item.class, null),
    ITEM_TITLE("title", Item.class, ITEM.getXmlTag()),
    ITEM_AUTHOR("dc:creator", Item.class, ITEM.getXmlTag()),
    ITEM_DESCRIPTION("description", Item.class, ITEM.getXmlTag()),
    ITEM_CONTENT("content:encoded", Item.class, ITEM.getXmlTag()),
    ITEM_EXCERPT("excerpt:encoded", Item.class, ITEM.getXmlTag()),
    ITEM_PUBLICATION_DATE_GMT("wp:post_date_gmt", Item.class, ITEM.getXmlTag()),
    ITEM_NAME("wp:post_name", Item.class, ITEM.getXmlTag()),
    ITEM_STATUS("wp:status", Item.class, ITEM.getXmlTag()),
    ITEM_TYPE("wp:post_type", Item.class, ITEM.getXmlTag()),
    ITEM_ATTACHMENT_URL("wp:attachment_url", ItemAttachment.class, ITEM.getXmlTag()),

    ITEM_POST_META("wp:postmeta", PostMeta.class, ITEM.getXmlTag()),
    ITEM_POST_META_KEY("wp:meta_key", PostMeta.class, ITEM_POST_META.getXmlTag()),
    ITEM_POST_META_VALUE("wp:meta_value", PostMeta.class, ITEM_POST_META.getXmlTag()),

    ITEM_COMMENT("wp:comment", Comment.class, ITEM.getXmlTag()),
    ITEM_COMMENT_ID("wp:comment_id", Comment.class, ITEM_COMMENT.getXmlTag()),
    ITEM_COMMENT_AUTHOR("wp:comment_author", Comment.class, ITEM_COMMENT.getXmlTag()),
    ITEM_COMMENT_AUTHOR_EMAIL("wp:comment_author_email", Comment.class, ITEM_COMMENT.getXmlTag()),
    ITEM_COMMENT_PUBLICATION_DATE_GMT("wp:comment_date_gmt", Comment.class, ITEM_COMMENT.getXmlTag()),
    ITEM_COMMENT_CONTENT("wp:comment_content", Comment.class, ITEM_COMMENT.getXmlTag())

    ;

    /** Real XML tag, as it appears in the file. */
    private String xmlTag;

    /** Corresponding class to work with for that tag */
    private Class clazz;

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

    WpCliXmlTag(final String xmlTag, final Class clazz, final String parent) {
        this.xmlTag = xmlTag;
        this.clazz = clazz;
        this.parent = parent;
    }

    public Class getClazz() {
        return clazz;
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
