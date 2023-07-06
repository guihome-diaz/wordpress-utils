package eu.daxiongmao.wordpress.wxr.model;

/**
 * Various type of items available in the website
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
public enum ItemType {

    /** File attachment */
    ATTACHMENT,

    /** Blog post */
    POST,

    /** Website page */
    PAGE,

    /** Better notifications for WordPress (plug-in) */
    BNFW_NOTIFICATION,

    /** Timeline Express # announcement */
    TE_ANNOUNCEMENTS,

    /** WordPress Contact Form 7 */
    WPCF7_CONTACT_FORM
    ;

}
