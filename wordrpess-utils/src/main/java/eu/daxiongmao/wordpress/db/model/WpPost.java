package eu.daxiongmao.wordpress.db.model;

import lombok.Data;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Wordpress core table: POSTS.
 * "The core of the WordPress data is the posts"
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz (based on Wordpress documentation and installation, see https://codex.wordpress.org/Database_Description)
 */
@Data
@Entity
@Table(name = "posts",
        uniqueConstraints = {
                @UniqueConstraint(name = "post_uq_id", columnNames = "ID")
        },
        indexes = {
                @Index(name = "post_idx_id", columnList = "ID", unique = true),
                @Index(name = "post_idx_author", columnList = "post_author asc"),
                @Index(name = "post_idx_name", columnList = "post_name asc"),
                @Index(name = "post_idx_parent", columnList = "post_parent"),
                @Index(name = "post_idx_type_status_date", columnList = "post_type asc, post_status, post_date, ID")
        }
)
public class WpPost implements Serializable {

    /** Date format in DB for post time (publication and updates) */
    public static final String POST_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** The identification number of the post.
     * Each post has a unique ID which allows us to identify a post conclusively using a single number
     */
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Integer id;

    /** Post's author. This is the USER_ID of the author.
     *  [i.e: an author must be a valid user of the blog]
     */
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_author", nullable = false)
    private WpUser author;

    /** Publication date. Format is: {@link #POST_TIME_FORMAT} */
    @NonNull
    @Column(name = "post_date", nullable = false)
    private ZonedDateTime postDate = ZonedDateTime.now(ZoneId.systemDefault());

    /** Publication date time, in GMT time zone [ie: GMT == UTC].
     * Format is: {@link #POST_TIME_FORMAT}<br>
     * This can be NULL or EMPTY because old versions of wordpress did not saved the GMT value. */
    @Column(name = "post_date_gmt")
    private ZonedDateTime postDateGmt = ZonedDateTime.now(ZoneOffset.UTC);

    /** Post content: full and unchanged content of the post.
     * This can contains many languages, code, plugins, scripts, etc.<br>
     * There is no size limit [mysql longtext size].<br>
     *     /!\ Please note that content might be NULL or EMPTY for particular post types.
     */
    @Column(name = "post_content")
    private String content;

    /**
     * Post title.
     */
    @NonNull
    @Column(name = "post_title", nullable = false, length = 65535)
    private String title;

    /** Post short description to be displayed on the main page, before the content. */
    @Column(name = "post_excerpt", length = 65535)
    private String excerpt;

    /** Post status. Default is 'published' */
    @NonNull
    @Column(name = "post_status", nullable = false, length = 20)
    private String status = WpPostStatus.PUBLISHED.getDbValue();

    /** Comment status. A post can allow | block comments.
     * This determines whether the post is open to commenting. */
    @NonNull
    @Column(name = "comment_status", nullable = false, length = 20)
    private String commentStatus = WpPostCommentStatus.OPEN.getDbValue();

    /** Determines whether the post is open to pingbacks. */
    @NonNull
    @Column(name = "ping_status", nullable = false, length = 20)
    private String pingStatus = WpPostPingStatus.OPEN.getDbValue();

    /** The password needed to access the post. Beware, this is stored as plain text! */
    @Column(name = "post_password", nullable = true, length = 255)
    private String password;

    /** The name of the post, generated from the title by replacing spaces with dashes and removing special characters. */
    @NonNull
    @Column(name = "post_name", nullable = false, length = 200)
    private String name;

    /** Contains a list of URLs to ping when the post is published.
     * This is a space or carriage-return separated list */
    @Column(name = "to_ping", length = 65535)
    private String toPing;

    /** Contains a list of URLs that have already been pinged.
     * This is a space or carriage-return separated list */
    @Column(name = "pinged", length = 65535)
    private String pinged;

    /** last modification date. Format is: {@link #POST_TIME_FORMAT} */
    @NonNull
    @Column(name = "post_modified", nullable = false)
    private ZonedDateTime modificationDate = ZonedDateTime.now(ZoneId.systemDefault());

    /** last modification date time, in GMT time zone [ie: GMT == UTC].
     * Format is: {@link #POST_TIME_FORMAT}<br>
     * This can be NULL or EMPTY because old versions of wordpress did not saved the GMT value. */
    @Column(name = "post_modified_gmt")
    private ZonedDateTime modificationDateGmt = ZonedDateTime.now(ZoneOffset.UTC);

    /**
     * <strong>Be very careful if you use that field!!</strong><br>
     * It stores a modified version of the post content.
     * It can essentially act as a cache for plugins which run very resource intensive modifications on content.
     * You’ll need to take care as this field is cleared on a number of user actions like updating the post.
     */
    @Column(name = "post_content_filtered")
    private String contentFiltered;

    /** Stores the ID of the parent post if it has one.
     * Put '0' if it does not apply. */
    @Column(name = "post_parent")
    private int parentId = 0;

    /** Contains the full URL of the post */
    @NonNull
    @Column(name = "guid", nullable = false, length = 255)
    private String postUrl;

    /**
     * Stores the menu order that can be set for pages.
     * If your post is not a PAGE, just set '0' */
    @Column(name = "menu_order")
    private int menuOrder = 0;

    /**
     * Type of the post. New values can be added by 3rd parties plugins.
     * Default is blog post.
     */
    @Column(name = "post_type", nullable = false, length = 20)
    private String type = WpPostType.POST.getDbValue();

    /**  The MIME type of uploaded media elements is stored in this field.<br>
     * This only apply to attachments.<br>
     * A JPG image would have a MIME type of image/jpeg for example
     */
    @Column(name = "mimeType", nullable = true, length = 100)
    private String mimeType;

    /**
     * Number of comments related to a particular post or page.
     * Put '0' if this is not applicable or if there are none
     */
    @Column(name = "comment_count")
    private int commentsCount = 0;

    /**
     * @return post status as ENUM value
     */
    public Optional<WpPostStatus> getStatusEnum() {
        if (StringUtils.isBlank(this.status)) {
            return Optional.empty();
        }
        return WpPostStatus.getStatus(this.status);
    }

    /**
     * @param status post status
     */
    public void setStatusEnum(WpPostStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("You must provide a valid status to set");
        }
        this.status = status.getDbValue();
    }

    /**
     * This determines whether the post is open to commenting
     * @return post comment status as ENUM value.
     */
    public Optional<WpPostCommentStatus> getCommentStatusEnum() {
        if (StringUtils.isBlank(this.status)) {
            return Optional.empty();
        }
        return WpPostCommentStatus.getStatus(this.status);
    }

    /**
     * @param status post comment status
     */
    public void setCommentStatusEnum(WpPostCommentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("You must provide a valid status to set");
        }
        this.status = status.getDbValue();
    }

    /**
     * Determines whether the post is open to pingback.
     * @return post comment status as ENUM value.
     */
    public Optional<WpPostPingStatus> getPingStatusEnum() {
        if (StringUtils.isBlank(this.status)) {
            return Optional.empty();
        }
        return WpPostPingStatus.getStatus(this.status);
    }

    /**
     * @param status post pingback status
     */
    public void setPingStatusEnum(WpPostPingStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("You must provide a valid status to set");
        }
        this.status = status.getDbValue();
    }

    /**
     * @return post type as Enum
     */
    public Optional<WpPostType> getTypeEnum() {
        if (StringUtils.isBlank(this.status)) {
            return Optional.empty();
        }
        return WpPostType.getStatus(this.status);
    }

    /**
     * @param type post type to set
     */
    public void setTypeEnum(WpPostType type) {
        if (type == null) {
            throw new IllegalArgumentException("You must provide a valid type to set");
        }
        this.status = type.getDbValue();
    }
}
