package eu.daxiongmao.wordrpess.model;

import java.io.Serializable;

/**
 * Item status.<br>
 *     <code>
 *         &lt;wp:postmeta&gt;<br>
 *             &nbsp;&nbsp;&lt;wp:meta_key&gt;key&lt;/wp:meta_key&gt;<br>
 *             &nbsp;&nbsp;&lt;wp:meta_value&gt;key&lt;/wp:meta_value&gt;<br>
 *         &lt;/wp:postmeta&gt;<br>
 *     </code>
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
public class PostMeta implements Serializable {

    /** Name of the meta. Field: &lt;wp:meta_key&gt; */
    private String key;

    /** Value of the meta. Field: &lt;wp:meta_value&gt; */
    private String value;

}
