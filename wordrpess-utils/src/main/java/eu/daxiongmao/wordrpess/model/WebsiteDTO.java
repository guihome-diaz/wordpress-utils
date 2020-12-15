package eu.daxiongmao.wordrpess.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Main attributes of the website
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
@Data
public class WebsiteDTO implements Serializable {

    /** Website title */
    private String title;

    /** Website root URL: this is the main page. */
    private String rootUrl;

    /** Website description (to be display in search engine). */
    private String description;

}
