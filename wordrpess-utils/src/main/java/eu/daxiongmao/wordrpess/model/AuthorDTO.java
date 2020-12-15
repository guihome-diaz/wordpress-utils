package eu.daxiongmao.wordrpess.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * An author is a CMS user. He can access the admin interface and perform various actions, according to his/her role.
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AuthorDTO implements Serializable {

    /** User login */
    @EqualsAndHashCode.Include
    private String login;

    /** Email */
    private String email;

    /** Nickname | shortname to use for that author. this is how publication will be signed. */
    private String displayName;

    /** First name */
    private String firstName;

    /** Last name */
    private String lastName;

}
