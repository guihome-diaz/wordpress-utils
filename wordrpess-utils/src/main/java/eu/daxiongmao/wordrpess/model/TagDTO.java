package eu.daxiongmao.wordrpess.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Tag.
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TagDTO implements Serializable {

    /** Tag slug. Unique string to identify this object in DB. It cannot contains spaces. */
    @EqualsAndHashCode.Include
    private String slug;

    /** Tag name. This is how the object name will appear on the website. It can contains spaces */
    private String name;

}
