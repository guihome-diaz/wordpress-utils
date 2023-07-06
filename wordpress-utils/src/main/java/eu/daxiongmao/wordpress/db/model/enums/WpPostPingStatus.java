package eu.daxiongmao.wordpress.db.model.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * WordPress' posts' pingbacks statuses
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz (based on WordPress documentation and installation, see https://WordPress.org/support/article/post-status/)
 */
public enum WpPostPingStatus {

    /** pingback is not allowed: if other website reference this article it will not appear on the blog */
    CLOSED("closed"),

    /** pingback is allowed. Other website can reference this article on their website */
    OPEN("open");

    private String dbValue;

    /**
     * @return status key to set in database
     */
    public String getDbValue() {
        return dbValue;
    }

    WpPostPingStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    /**
     * To retrieve the corresponding WordPress posts' pingback status.
     * @param dbValue search value. It must not be null or blank
     * @return corresponding status, if it exists. Else EMPTY
     * @throws IllegalArgumentException you must provide a not null and not blank argument
     */
    public static Optional<WpPostPingStatus> getStatus(final String dbValue) {
        // arg check
        if (StringUtils.isBlank(dbValue)) {
            throw new IllegalArgumentException("You must provide a status to evaluate");
        }

        for (WpPostPingStatus item : values()) {
            if (item.getDbValue().equals(dbValue)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

}
