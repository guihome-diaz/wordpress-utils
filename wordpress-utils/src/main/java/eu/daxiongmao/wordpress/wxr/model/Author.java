package eu.daxiongmao.wordpress.wxr.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * An author is a CMS user.
 * He can access the admin interface and perform various actions, according to his/her role.
 * Field: &lt;wp:author&gt;
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Author implements Serializable {

    /** User ID. Field &lt;wp:author_id&gt;
     */
    private Integer id;

    /** User login. Field: &lt;wp:author_login&gt; */
    @EqualsAndHashCode.Include
    private String login;

    /** Email. Field: &lt;wp:author_email&gt; */
    private String email;

    /** Nickname | shortname to use for that author.
     * this is how publication will be signed.
     * Field: &lt;wp:author_display_name&gt; */
    private String displayName;

    /** First name. &lt;wp:author_first_name&gt; */
    private String firstName;

    /** Last name. &lt;wp:author_last_name&gt; */
    private String lastName;

}
