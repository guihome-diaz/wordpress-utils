package eu.daxiongmao.wordrpess.model;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Main attributes of the website
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
@Data
public class Website implements Serializable {

    /** Website title */
    private String title;

    /** Website root URL: this is the main page. */
    private String rootUrl;

    /** Website description (to be display in search engine). */
    private String description;

    /** Website's categories. Each post can be linked to one or many categories. Fields: &lt;wp:category&gt; */
    private final Set<Category> categories = new HashSet<>();

    /** Website's authors. Each author can have many posts. Fields: &lt;wp:author&gt; */
    private final Set<Author> authors = new HashSet<>();

}
