package eu.daxiongmao.wordpress.db.model.enums;


import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Wordpress posts' statuses
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz (based on Wordpress documentation and installation, see https://wordpress.org/support/article/post-status/)
 */
public enum WpPostStatus {

    /** Post just created and it hasn’t had any previous status */
    NEW("new"),

    /** Posts that are published and visible to everyone (including users who are logged out).
     * Viewable by everyone.
     * WordPress sets the post status to publish when you click the “Publish” button */
    PUBLISHED("published"),

    /** Scheduled to be published in a future date */
    FUTURE("future"),

    /** Incomplete post viewable by anyone with proper user role (draft).
     * WordPress sets the post status to draft when you click the “Save Draft” button */
    DRAFT("draft"),

    /** Awaiting a user with the "publish_posts" capability (typically a user assigned the Editor role) to publish */
    PENDING("pending"),

    /** Viewable only to WordPress users at Administrator level */
    PRIVATE("private"),

    /** Posts in the Trash are assigned the trash status */
    TRASH("trash"),

    /** Revisions that WordPress saves automatically while you are editing */
    AUTO_DRAFT("auto-draft"),

    /** Used with a child post (such as Attachments and Revisions) to determine the actual status from the parent post (inherit) */
    INHERIT("inherit");

    private String dbValue;

    /**
     * @return status key to set in database
     */
    public String getDbValue() {
        return dbValue;
    }

    WpPostStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    /**
     * To retrieve the corresponding Wordpress status.
     * @param dbValue search value. It must not be null or blank
     * @return corresponding status, if it exists. Else EMPTY
     * @throws IllegalArgumentException you must provide a not null and not blank argument
     */
    public static Optional<WpPostStatus> getStatus(final String dbValue) {
        // arg check
        if (StringUtils.isBlank(dbValue)) {
            throw new IllegalArgumentException("You must provide a status to evaluate");
        }

        for (WpPostStatus item : values()) {
            if (item.getDbValue().equals(dbValue)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }
}
