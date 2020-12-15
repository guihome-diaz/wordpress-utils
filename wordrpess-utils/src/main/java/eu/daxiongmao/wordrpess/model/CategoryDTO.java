package eu.daxiongmao.wordrpess.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Website category.
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoryDTO {

    /** Category slug. Unique string to identify this object in DB. It cannot contains spaces. */
    @EqualsAndHashCode.Include
    private String slug;

    /** Category name. This is how the object name will appear on the website. It can contains spaces */
    private String name;

    /** Optional link to a parent category */
    private CategoryDTO parent;

}
