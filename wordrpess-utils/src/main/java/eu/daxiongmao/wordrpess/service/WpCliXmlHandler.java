package eu.daxiongmao.wordrpess.service;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Stack;

/**
 * To parse the XML file from Wordpress WP-CLI (WXR files)
 * @version 1.0 (based on WP-CLI WXR export)
 * @author Guillaume Diaz
 * @since 2020-12
 */
public class WpCliXmlHandler extends DefaultHandler {

    /** Name of the current tag that is being processed. */
    private WpCliXmlTag currentTag = null;

    /** As we read any XML element we will push that element in the current stack.<br>
     * A stack is a last-in-first-out (LIFO) queue. */
    private final Stack<String> xmlElementStack = new Stack<>();

    /** Set of objects that are being populate, according to the XML order.<br>
     * A stack is a last-in-first-out (LIFO) queue. */
    private final Stack<Object> xmlObjectStack = new Stack<>();

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        // Get corresponding WP-Cli tag
        currentTag = WpCliXmlTag.getTag(qName, currentTag);

        // Add element name in the LIFO queue
        xmlElementStack.push(qName);

        // Init object
        // FIXME to be continued (https://howtodoinjava.com/java/xml/sax-parser-read-xml-example/)
        if (currentTag != null) {
            switch (currentTag) {
                case WEBSITE_TITLE:
                    break;
                case WEBSITE_DESCRIPTION:
                    break;
                case WEBSITE_ROOT_URL:
                    break;

                case AUTHOR:
                    break;
                case AUTHOR_LOGIN:
                    break;
                case AUTHOR_EMAIL:
                    break;
                case AUTHOR_DISPLAY_NAME:
                    break;
                case AUTHOR_FIRST_NAME:
                    break;
                case AUTHOR_LAST_NAME:
                    break;

                case CATEGORY:
                    break;
                case CATEGORY_SLUG:
                    break;
                case CATEGORY_NAME:
                    break;
                case CATEGORY_PARENT:
                    break;

                case ITEM:
                    break;
                case ITEM_TITLE:
                    break;
                case ITEM_AUTHOR:
                    break;
                case ITEM_DESCRIPTION:
                    break;
                case ITEM_CONTENT:
                    break;
                case ITEM_EXCERPT:
                    break;
                case ITEM_PUBLICATION_DATE_GMT:
                    break;
                case ITEM_NAME:
                    break;
                case ITEM_STATUS:
                    break;
                case ITEM_TYPE:
                    break;
                case ITEM_ATTACHMENT_URL:
                    break;

                case ITEM_POST_META:
                    break;
                case ITEM_POST_META_KEY:
                    break;
                case ITEM_POST_META_VALUE:
                    break;

                case ITEM_COMMENT:
                    break;
                case ITEM_COMMENT_ID:
                    break;
                case ITEM_COMMENT_AUTHOR:
                    break;
                case ITEM_COMMENT_AUTHOR_EMAIL:
                    break;
                case ITEM_COMMENT_PUBLICATION_DATE_GMT:
                    break;
                case ITEM_COMMENT_CONTENT:
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    @Override
    public void characters(final char[] ch, final int start, final int length) throws SAXException {
        super.characters(ch, start, length);
    }
}
