package eu.daxiongmao.wordpress.wxr.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Main attributes of the website
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
@Data
public class Website implements Serializable {

    /** Website title. Field: &lt;title&gt; */
    private String title;

    /** Website root URL: this is the main page. Field: &lt;link&gt; */
    private String url;

    /** Website description (to be display in search engine). Field: &lt;description&gt; */
    private String description;

    /** Website main language (can be changed later on). Field: &lt;language&gt; */
    private String language;

    /** URL of website that has been saved */
    private String wordpressBaseUrl;

    /** Website's categories.
     * Each post can be linked to one or many categories.<br>
     * Fields: &lt;wp:category&gt;<br><br>
     *     Categories are in a dictionary:
     *     <ul>
     *         <li>Key: category slug (unique name)</li>
     *         <li>Value: category</li>
     *     </ul>
     *     This construction allows us to build the relationship faster afterwards.
     */
    private final Map<String, Category> categories = new HashMap<>();

    /** Website's tags. Each post can be linked to one or many tags. Fields: &lt;wp:tag&gt; */
    private final Set<Tag> tags = new HashSet<>();

    /** Website's authors. Each author can have many posts. Fields: &lt;wp:author&gt; */
    private final Set<Author> authors = new HashSet<>();

    /** Website's items. */
    private final Set<Item> items = new HashSet<>();

    /**
     * To update categories relationships.
     * This will link all child to their corresponding parent
     */
    public void updateCategoriesRelationships() {
        categories.forEach((slug, cat) -> {
            if (StringUtils.isNotBlank(cat.getParentCategorySlug())) {
                cat.setParent(categories.get(slug));
            }
        });
    }
}
