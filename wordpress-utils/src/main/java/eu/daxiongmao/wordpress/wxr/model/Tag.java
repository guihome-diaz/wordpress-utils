package eu.daxiongmao.wordpress.wxr.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Tag, Field: &lt;wp:tag&gt;.
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tag implements Serializable {

    /** Tag ID. Field &lt;wp:term_id&gt; */
    private Integer id;

    /** Tag slug. Unique string to identify this object in DB. It cannot contains spaces. Field: &lt;wp:tag_slug&gt; */
    @EqualsAndHashCode.Include
    private String slug;

    /** Tag name. This is how the object name will appear on the website. It can contains spaces. Field: &lt;wp:tag_name&gt; */
    private String name;

}
