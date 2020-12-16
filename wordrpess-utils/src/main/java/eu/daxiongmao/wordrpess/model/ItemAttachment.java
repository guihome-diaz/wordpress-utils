package eu.daxiongmao.wordrpess.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Attachment.
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ItemAttachment extends Item {

    /** Attachment URL. Field &lt;wp:attachment_url&gt; */
    private String attachmentUrl;
}
