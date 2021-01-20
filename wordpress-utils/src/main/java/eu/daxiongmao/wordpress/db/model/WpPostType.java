package eu.daxiongmao.wordpress.db.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Wordpress posts' types
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz (based on Wordpress documentation and installation)
 */
public enum WpPostType {

    // ####################################################
    // Wordpress core features
    // ####################################################

    /** Link to embedded media (picture, video) inside the post.
     * (i) Media does not need to be INSIDE the content.
     * eg: they can be the blog picture on the main page */
    ATTACHMENT("attachment"),

    /** Website page. The content will be display in full. */
    PAGE("page"),

    /** Blog post. This will be displayed chronologically by default. */
    POST("post"),

    /**
     * Revisions is a post type and a feature in WordPress.
     * It enables WordPress to automatically save revisions of your posts, pages, or custom post types after
     * every 60 second while a user is working on them. <br>
     *     Revisions were added in WordPress for two very important reasons.
     *     <ul>
     *         <li>One of them was to prevent the risk of losing data.<br>
     *             Computers are weird and unpredictable.
     *             You never know when your browser will crash or if your computer will restart automatically.
     *             There are also situations when you accidentally close the browser window or refresh the page.
     *             Thanks to Revisions, you don’t have to constantly save everything: WordPress does it for you.
     *          </li>
     *          <li>The other important reason for adding revisions was to improve the editorial workflow.
     *              There are times in writing when you make mistakes or get rid of an entire idea without thinking it through.
     *              A day or two later, you want to go back to that initial idea.
     *              Well revisions allow you to compare your post versions.
     *              You can even restore an older version of your post if you like.
     *          </li>
     * </ul>
     */
    REVISION("revision"),

    // ####################################################
    // Themes
    // ####################################################

    /** Custom settings for the SYNAPSE theme */
    THEME_SYNAPSE("customize_changeset"),


    // ####################################################
    // Plugins
    // ####################################################

    /** Better Notifications pour WordPress (plugin specific).
     * These are notifications to send to the user, by email. */
    PLUGIN_BETTER_NOTIFICATION_FOR_WORDPRESS("bnfw_notification"),

    /** Media Library Folders plugin. List of folders to scan for new media content. */
    PLUGIN_MEDIA_LIBRARY_FOLDER("mgmlp_media_folder"),

    /** Title of the different galleries types of NextGen Galleries plugin */
    PLUGIN_NGG_TITLE("display_type"),

    /** Particular configuration for a Gallery from NextGen Galleries plugin.
     * Each gallery can be customized and have particular rendering.
     * The content is the JSON settings, encoded in Base64
     */
    PLUGIN_NGG_GALLERY("ngg_gallery"),

    /**
     * Particular configuration for an image in NextGen Galleries plugin.
     * All images can have particular rendering. These default settings will override the ALBUM or GALLERY settings.
     * The content is the JSON settings, encoded in Base64
     */
    PLUGIN_NGG_PICTURES("ngg_pictures"),

    /**
     * The NextGen Galleries PRO offer the ability to buy / download photos.
     * Each price has a particular configuration in DB.
     * The content is the JSON settings, encoded in Base64
     */
    PLUGIN_NGG_PRICELIST("ngg_pricelist"),

    /**
     * The NextGen Galleries PRO offer the ability to buy / download photos.
     * This represents a print size.
     */
    PLUGIN_NGG_PRICELIST_ITEM("ngg_pricelist_item"),

    PLUGIN_NGG_PHOTOCRATI_COMMENTS("photocrati-comments"),

    /** Timeline express announcement.
     * An announcement is a post that is part of a particular timeline.
     */
    PLUGIN_TIMELINE_EXPRESS("te_announcements"),

    /** Website's contact form. */
    PLUGIN_CONTACT_FORM_7("wpcf7_contact_form");

    private String dbValue;

    /**
     * @return type key to set in database
     */
    public String getDbValue() {
        return dbValue;
    }

    WpPostType(String dbValue) {
        this.dbValue = dbValue;
    }

    /**
     * To retrieve the corresponding Wordpress posts' type.
     * @param dbValue search value. It must not be null or blank
     * @return corresponding status, if it exists. Else EMPTY
     * @throws IllegalArgumentException you must provide a not null and not blank argument
     */
    public static Optional<WpPostType> getStatus(final String dbValue) {
        // arg check
        if (StringUtils.isBlank(dbValue)) {
            throw new IllegalArgumentException("You must provide a status to evaluate");
        }

        for (WpPostType item : values()) {
            if (item.getDbValue().equals(dbValue)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

}
