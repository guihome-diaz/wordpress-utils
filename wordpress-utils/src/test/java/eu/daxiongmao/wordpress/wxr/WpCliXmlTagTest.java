package eu.daxiongmao.wordpress.wxr;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * test related to XML tags.
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
public class WpCliXmlTagTest {

    @Test
    public void getSimpleTag() {
        Assertions.assertEquals(WpCliXmlTag.WEBSITE, WpCliXmlTag.getTag("channel", null));
        Assertions.assertEquals(WpCliXmlTag.AUTHOR_DISPLAY_NAME, WpCliXmlTag.getTag(WpCliXmlTag.AUTHOR_DISPLAY_NAME.getXmlTag(), WpCliXmlTag.AUTHOR));
    }
}
