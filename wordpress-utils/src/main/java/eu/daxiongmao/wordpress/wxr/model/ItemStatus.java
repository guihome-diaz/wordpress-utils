package eu.daxiongmao.wordpress.wxr.model;

/**
 * Item status. &lt;wp:status&gt;
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
public enum ItemStatus {

    /** Published: it should appear on the website */
    PUBLISHED,

    /** Draft: do not display this item on the website yet. */
    DRAFT;

}
