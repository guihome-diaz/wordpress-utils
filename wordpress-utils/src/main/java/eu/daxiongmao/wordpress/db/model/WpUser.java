package eu.daxiongmao.wordpress.db.model;

import eu.daxiongmao.wordpress.db.utils.DbDataValidationUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Wordpress core table: USERS
 * @version 1.0
 * @since 2020/12
 * @author Guillaume Diaz (based on Wordpress documentation and installation, see https://codex.wordpress.org/Database_Description)
 */
@Data
@Entity
@Table(name = "users",
        uniqueConstraints = {
            @UniqueConstraint(name = "user_uq_login", columnNames = "user_login"),
            @UniqueConstraint(name = "user_uq_email", columnNames = "user_email")
        },
        indexes = {
            @Index(name = "user_idx_id", columnList = "ID", unique = true),
            @Index(name = "user_idx_login_key", columnList = "user_login asc", unique = true),
            @Index(name = "user_idx_email", columnList = "user_email asc", unique = true),
            @Index(name = "user_idx_nicename", columnList = "user_nicename asc")
        }
)
@NoArgsConstructor
public class WpUser implements Serializable {

    /** Date format in DB for user registration time */
    public static final String USER_REGISTRATION_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    /** User login can be a short name OR an email address. */
    @NonNull
    @Column(name = "user_login", nullable = false, length = 60, unique = true)
    private String login;

    /** Encrypted user password. */
    @NonNull
    @Column(name = "user_pass", nullable = false, length = 255)
    private String password;

    /** <code>nicename</code> is url sanitized version of <code>login</code>.
     * In general, if you don't use any special characters in your login, then your <i>nicename</i> will always be the same as <i>login</i>.
     * But if you enter email address in the login field during registration, then you will see the difference.
     */
    @NonNull
    @Column(name = "user_nicename", nullable = false, length = 50)
    private String niceName;

    @NonNull
    @Column(name = "user_email", nullable = false, length = 100, unique = true)
    private String email;

    /** This is an optional field that can be used to store the user website URL.
     * Many themes use this URL in the comments section, by linking the user’s avatar and/or name to their website.
     */
    @Column(name = "user_url", nullable = true, length = 100)
    private String url = "";

    /** Date-time of the user registration. Format is: {@link #USER_REGISTRATION_TIME_FORMAT} */
    @NonNull
    @Column(name = "user_registered", nullable = false)
    private ZonedDateTime registrationTime = ZonedDateTime.now(ZoneId.systemDefault());

    /** WordPress uses this field to store unique keys for password reset requests. */
    @NonNull
    @Column(name = "user_activation_key", nullable = false, length = 255)
    private String activationKey;

    /** This field was used to store the status of a user in the form of integers (0, 1 or 2), it is not used anymore.
     * It is now a deprecated field and most likely will be removed from future versions.
     * <ul>
     *     <li><strong>0</strong> is ok</li>
     *     <li><strong>1</strong> is spam</li>
     * </ul>
     * Default is 0
     */
    @Deprecated
    @Column(name = "user_status")
    private int status = 0;

    /** This is how the user name will appear on the website.
     * It can be a nickname with spaces and special characters. */
    @NonNull
    @Column(name = "display_name", nullable = false, length = 250)
    private String displayName;

    /**
     * @param email user email to set. It must not be blank, and it must be a valid email according to current RFCs.
     */
    public void setEmail(String email) {
        DbDataValidationUtils.checkEmail(email);
        this.email = email.trim();
    }

    /**
     * Method called before every persistence operation to sanitize data.
     * Better save some data as lowercase: it will speed up the indexes searches if the case is always the same.
     */
    @PrePersist @PreUpdate
    public void prepareData(){
        this.email = this.email.trim().toLowerCase();
        this.login = this.login.trim().toLowerCase();
    }
}
