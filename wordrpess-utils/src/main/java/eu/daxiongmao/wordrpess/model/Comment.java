package eu.daxiongmao.wordrpess.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Post's comment.<br>
 *  <code>
 *  &lt;comment&gt;<br>
 *  ...
 *  &lt;/comment&gt;<br>
 *  </code>
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comment implements Serializable {

    /** Comment's ID. &lt;wp:comment_id&gt; */
    @EqualsAndHashCode.Include
    private Integer id;

    /** Comment's author. Field: &lt;wp:comment_author&gt; */
    private String author;

    /** Comment's author's email. Field: &lt;wp:comment_author_email&gt; */
    private String authorEmail;

    /** Comment's date (gmt). Field: &lt;wp:comment_date_gmt&gt; */
    private ZonedDateTime publicationDateGmt;

    /** Comment's content. Field: &lt;wp:comment_content&gt; */
    private String content;

}
