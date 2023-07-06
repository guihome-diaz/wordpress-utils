package eu.daxiongmao.wordpress.db.model;

import eu.daxiongmao.wordpress.db.utils.DbDataValidationUtils;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * WordPress core table: COMMENTS
 * A comment is linked to a particular post ID
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz (based on WordPress documentation and installation, see https://codex.wordpress.org/Database_Description)
 */
@Data
@Entity
@Table(name = "comments",
        uniqueConstraints = {
                @UniqueConstraint(name = "comment_uq_id", columnNames = "comment_ID")
        },
        indexes = {
            @Index(name = "comment_idx_id", columnList = "comment_ID", unique = true),
            @Index(name = "comment_idx_parent", columnList = "comment_parent asc"),
            @Index(name = "comment_idx_date_gmt", columnList = "comment_date_gmt asc"),
            @Index(name = "comment_idx_email", columnList = "comment_author_email asc"),
            @Index(name = "comment_idx_approved_date_gmt", columnList = "comment_approved asc, comment_author_email asc"),
            @Index(name = "comment_idx_user_id", columnList = "user_id asc")
        }
)
@NoArgsConstructor
public class WpComment extends PanacheEntityBase implements Serializable {

    /** Date format in DB for user registration time */
    public static final String COMMENT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_ID", updatable = false, nullable = false)
    private Long id;

    // FIXME set a better relationship
    @NonNull
    @Column(name = "comment_post_ID", updatable = false, nullable = false)
    private Long post;

    /** Comment's author's display name */
    @NonNull
    @Column(name = "comment_author", nullable = false, length = 255)
    private String authorName;

    /** Comment's author's email. Optional value. */
    @Column(name = "comment_author_email", nullable = true, length = 100)
    private String authorEmail;

    /** Comment's author's website. Optional value. */
    @Column(name = "comment_author_url", nullable = true, length = 200)
    private String authorUrl;

    /** Comment's author's IP@. This value is set automatically by wordpress, it is required for tracking purposes.
     * It also avoids spam coming from suspicious countries.
     * Default empty value.*/
    @NonNull
    @Column(name = "comment_author_ip", nullable = false, length = 100)
    private String authorIp = "";

    /** Date-time of the comment. Format is: {@link #COMMENT_TIME_FORMAT} */
    @NonNull
    @Column(name = "comment_date", nullable = false)
    private ZonedDateTime commentDate = ZonedDateTime.now(ZoneId.systemDefault());

    /** Date-time of the comment, in GMT time zone [ie: GMT == UTC].
     * Format is: {@link #COMMENT_TIME_FORMAT}<br>
     *     This can be NULL or EMPTY because old versions of wordpress did not saved the GMT value.
     */
    @Column(name = "comment_date_gmt")
    private ZonedDateTime commentDateGmt = ZonedDateTime.now(ZoneOffset.UTC);

    /** Text to display.
     * This text can include HTML, code snippets, and lots of strange characters.
     * It supports all languages.
     * BLOB	max size: 64 Kb
     */
    @NonNull
    @Lob
    @Column(columnDefinition="text", name = "comment_content", nullable = false, length = 65535)
    // TODO add lazy loading on BLOB
    private String content;

    /**
     * Comment approval status. This is a BOOLEAN (0 or 1). By default all comments are approved automatically (1).
     * <ul>
     *     <li>"1" if the comment is approved</li>
     *     <li>"0" if the comment is pending approval or if it has been rejected</li>
     *  </ul>
     */
    @Column(name = "comment_approved", nullable = false, length = 20)
    private String approved = "1";

    /** Author's user agent. This is the nam eof the browser and hardware he used to write the comment. */
    @NonNull
    @Column(name = "comment_agent", nullable = false, length = 255)
    private String commentAgent;

    /**
     * Mandatory field since Wordpress 5.5 (August 2020).<br>
     *     According to the <a href="https://developer.wordpress.org/reference/functions/wp_list_comments/">official documentation</a>,
     *     it accepts:
     *     <ul>
     *         <li>'all'</li>
     *         <li>'comment' for a standard comment</li>
     *         <li>'pingback' allows you to notify other bloggers that you have linked to their article on your website.<br>
     *              They are automated and don’t send any content<br>
     *         see <a href="https://make.wordpress.org/support/user-manual/building-your-wordpress-community/trackbacks-and-pingbacks/">official documentation</a></li>
     *         <li>'trackback' like a <i>pingback</i> but they are manually created.
     *         Trackbacks are a way to notify legacy blog systems that you’ve linked to them</li>
     *         <li>'pings'</li>
     *     </ul>
     * Default value is 'comment'.
     */
    @NonNull
    @Column(name = "comment_type", nullable = false, length = 20)
    private String commentType;

    /**
     * Optional. Id of the parent's comment. Put '0' if it does not apply.
     * This is only used if the current comment is a response to another comment.
     */
    @Column(name = "comment_parent")
    private long parentId = 0;

    /**
     * Optional, default '0' for unknown users.
     * If the author is a blog user, then the user_id will be the link between USERS and COMMENTS tables
     */
    @Column(name = "user_id")
    private long userId = 0;

    /**
     * @param email user email to set. It must not be blank, and it must be a valid email according to current RFCs.
     */
    public void setAuthorEmail(String email) {
        DbDataValidationUtils.checkEmail(email);
        this.authorEmail = email.trim();
    }

    /**
     * @param authorIp to set the author's IP address. You must provide a not blank, valid IPv4 or IPv6.
     */
    public void setAuthorIp(String authorIp) {
        DbDataValidationUtils.checkIp(authorIp);
        this.authorIp = authorIp.trim();
    }

    /**
     * Method called before every persistence operation to sanitize data.
     * Better save some data as lowercase: it will speed up the indexes searches if the case is always the same.
     */
    @PrePersist @PreUpdate
    public void prepareData(){
        // Author email must always be in the same format as User.email because that's the link between the 2 tables
        this.authorEmail = this.authorEmail.trim().toLowerCase();
    }
}
