package eu.daxiongmao.wordrpess.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Website category. &lt;wp:category&gt;
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category implements Serializable {

    /** Category slug.
     * Unique string to identify this object in DB. It cannot contains spaces.
     * Field: &lt;wp:category_nicename&gt; */
    @EqualsAndHashCode.Include
    private String slug;

    /** Category name.
     * This is how the object name will appear on the website. It can contains spaces.
     * Field: &lt;wp:cat_name&gt; */
    private String name;

    /** Optional link to a parent category. &lt;wp:category_parent&gt; */
    private Category parent;

}
