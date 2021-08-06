package eu.daxiongmao.wordpress.db.model.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Wordpress' posts' comments statuses
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz (based on Wordpress documentation and installation, see https://wordpress.org/support/article/post-status/)
 */
public enum WpPostCommentStatus {

    /** Comments are not allowed on this post. */
    CLOSED("closed"),

    /** Comments allowed */
    OPEN("open");

    private String dbValue;

    /**
     * @return status key to set in database
     */
    public String getDbValue() {
        return dbValue;
    }

    WpPostCommentStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    /**
     * To retrieve the corresponding Wordpress posts' comment status.
     * @param dbValue search value. It must not be null or blank
     * @return corresponding status, if it exists. Else EMPTY
     * @throws IllegalArgumentException you must provide a not null and not blank argument
     */
    public static Optional<WpPostCommentStatus> getStatus(final String dbValue) {
        // arg check
        if (StringUtils.isBlank(dbValue)) {
            throw new IllegalArgumentException("You must provide a status to evaluate");
        }

        for (WpPostCommentStatus item : values()) {
            if (item.getDbValue().equals(dbValue)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

}
