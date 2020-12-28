package eu.daxiongmao.wordpress.wxr.xml;

import eu.daxiongmao.wordpress.wxr.WpCliXmlTag;
import eu.daxiongmao.wordpress.wxr.model.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

/**
 * To parse the XML file from Wordpress WP-CLI (WXR files).
 * FIXME to be continued (https://howtodoinjava.com/java/xml/sax-parser-read-xml-example/)
 *
 * @author Guillaume Diaz
 * @version 1.0 (based on WP-CLI WXR export)
 * @since 2020-12
 */
@Log4j2
public class WpCliXmlHandler extends DefaultHandler {

    private static final String ITEM_CATEGORY_ATTRIBUTE_DOMAIN = "domain";
    private static final String ITEM_CATEGORY_ATTRIBUTE_NICENAME = "nicename";
    private static final String ITEM_CATEGORY_DOMAIN_TAG = "post_tag";
    private static final String ITEM_CATEGORY_DOMAIN_CATEGORY = "category";
    private static final String GMT_ZONE_ID = "Etc/UTC";

    /** Current XML root and all the previous hierarchy. */
    private final Stack<WpCliXmlTag> currentXmlTag = new Stack<>();

    /**
     * As we read any XML element we will push that element in the current stack.<br>
     * A stack is a last-in-first-out (LIFO) queue.
     */
    private final Stack<String> xmlElementStack = new Stack<>();

    /**
     * Set of objects that are being populate, according to the XML order.<br>
     * A stack is a last-in-first-out (LIFO) queue.
     */
    private final Stack<Object> objectStack = new Stack<>();

    /**
     * Date formatter
     */
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Result object.
     */
    private Website website = null;

    @Override
    public void startDocument() throws SAXException {
        // initialize the root object
        final Website website = new Website();
        this.website = website;
        objectStack.push(website);
        log.debug("Start XML file parsing");
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        // Get corresponding WP-Cli tag
        final WpCliXmlTag tagToProcess = WpCliXmlTag.getTag(qName, (currentXmlTag.empty() ? null : currentXmlTag.peek()));

        // Add element name in the LIFO queue
        xmlElementStack.push(qName);

        // Init objects on specific tags
        if (tagToProcess != null) {
            currentXmlTag.push(tagToProcess);
            log.trace("Start tag: {} | enum value: {}", qName, tagToProcess);

            switch (tagToProcess) {
                // ***** New objects *****
                case AUTHOR:
                    Author author = new Author();
                    this.objectStack.push(author);
                    break;
                case CATEGORY:
                    Category category = new Category();
                    this.objectStack.push(category);
                    break;
                case TAG:
                    Tag tag = new Tag();
                    this.objectStack.push(tag);
                    break;
                case ITEM:
                    Item item = new Item();
                    this.objectStack.push(item);
                    break;
                case ITEM_POST_META:
                    PostMeta postMeta = new PostMeta();
                    this.objectStack.push(postMeta);
                    break;
                case ITEM_COMMENT:
                    Comment comment = new Comment();
                    this.objectStack.push(comment);
                    break;
                // ***** Extract attributes *****
                case ITEM_CATEGORY:
                    extractItemCategory(attributes);
                    break;

                default:
                    // Do nothing for all other attributes
                    break;
            }
        }
    }

    private void extractItemCategory(final Attributes attributes) {
        if (attributes == null || attributes.getLength() == 0) {
            return;
        }

        // Get indexes
        final int domainIndex = (attributes.getIndex(ITEM_CATEGORY_ATTRIBUTE_DOMAIN));
        final int nicenameIndex = (attributes.getIndex(ITEM_CATEGORY_ATTRIBUTE_NICENAME));
        if (domainIndex < 0 || nicenameIndex < 0) {
            // some attributes are missing
            return;
        }

        // Get value
        final String domain = attributes.getValue(domainIndex);
        final String nicename = attributes.getValue(nicenameIndex);
        if (StringUtils.isBlank(domain) || StringUtils.isBlank(nicename)) {
            // No value to register
            return;
        }

        // Save value
        if (ITEM_CATEGORY_DOMAIN_TAG.equals(domain)) {
            ((Item) this.objectStack.peek()).getTags().add(nicename.trim());
        } else if (ITEM_CATEGORY_DOMAIN_CATEGORY.equals(domain)) {
            ((Item) this.objectStack.peek()).getCategories().add(nicename.trim());
        }
    }

    @Override
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {
        // Register object on their corresponding parents
        if (!currentXmlTag.empty() && currentXmlTag.peek() != null) {
            if (!currentXmlTag.peek().getXmlTag().equals(qName)) {
                log.trace(" .. tag {} is not complete yet (qName: {})", currentXmlTag.peek(), qName);
                return;
            } else {
                log.trace("completion tag {} | enum value: {}", currentXmlTag.peek().getXmlTag(), currentXmlTag.peek());
            }

            switch (currentXmlTag.peek()) {
                // ***** New objects *****
                case AUTHOR:
                    final Author author = ((Author) this.objectStack.pop());
                    ((Website) this.objectStack.peek()).getAuthors().add(author);
                    log.debug("Completion AUTHOR: {}", author);
                    break;
                case CATEGORY:
                    final Category category = ((Category) this.objectStack.pop());
                    if (StringUtils.isNotBlank(category.getParentCategorySlug())) {
                        category.setParent(website.getCategories().get(category.getParentCategorySlug()));
                    }
                    ((Website) this.objectStack.peek()).getCategories().put(category.getSlug(), category);
                    log.debug("Completion CATEGORY: {}", category);
                    break;
                case TAG:
                    final Tag tag = ((Tag) this.objectStack.pop());
                    ((Website) this.objectStack.peek()).getTags().add(tag);
                    log.debug("Completion TAG: {}", tag);
                    break;
                case ITEM:
                    final Item item = ((Item) this.objectStack.pop());
                    ((Website) this.objectStack.peek()).getItems().add(item);
                    log.debug("Completion ITEM: {}", item);
                    break;
                case ITEM_POST_META:
                    final PostMeta postMeta = ((PostMeta) this.objectStack.pop());
                    ((Item) this.objectStack.peek()).getMetadata().add(postMeta);
                    log.debug("Completion POST_META: {}", postMeta);
                    break;
                case ITEM_COMMENT:
                    final Comment comment = ((Comment) this.objectStack.pop());
                    ((Item) this.objectStack.peek()).getComments().add(comment);
                    log.debug("Completion COMMENT: {}", comment);
                    break;
                default:
                    // Do nothing for all other attributes
                    break;
            }
            currentXmlTag.pop();
        }
    }

    @Override
    public void characters(final char[] ch, final int start, final int length) throws SAXException {
        final String value = new String(ch, start, length).trim();
        if (StringUtils.isBlank(value)) {
            // Ignore empty values
            return;
        }

        // Set object values
        if (!currentXmlTag.empty() && currentXmlTag.peek() != null) {
            switch (currentXmlTag.peek()) {
                case WEBSITE_TITLE:
                    ((Website) this.objectStack.peek()).setTitle(value);
                    break;
                case WEBSITE_DESCRIPTION:
                    ((Website) this.objectStack.peek()).setDescription(value);
                    break;
                case WEBSITE_URL:
                    ((Website) this.objectStack.peek()).setUrl(value);
                    break;
                case WEBSITE_LANGUAGE:
                    ((Website) this.objectStack.peek()).setLanguage(value);
                    break;

                case AUTHOR_ID:
                    ((Author) this.objectStack.peek()).setId(Integer.valueOf(value));
                case AUTHOR_DISPLAY_NAME:
                    ((Author) this.objectStack.peek()).setDisplayName(value);
                    break;
                case AUTHOR_EMAIL:
                    ((Author) this.objectStack.peek()).setEmail(value);
                    break;
                case AUTHOR_FIRST_NAME:
                    ((Author) this.objectStack.peek()).setFirstName(value);
                    break;
                case AUTHOR_LAST_NAME:
                    ((Author) this.objectStack.peek()).setLastName(value);
                    break;
                case AUTHOR_LOGIN:
                    ((Author) this.objectStack.peek()).setLogin(value);
                    break;

                case CATEGORY_ID:
                    ((Category) this.objectStack.peek()).setId(Integer.valueOf(value));
                    break;
                case CATEGORY_NAME:
                    ((Category) this.objectStack.peek()).setName(value);
                    break;
                case CATEGORY_SLUG:
                    ((Category) this.objectStack.peek()).setSlug(value);
                    break;
                case CATEGORY_PARENT:
                    ((Category) this.objectStack.peek()).setParentCategorySlug(value);
                    break;

                case TAG_ID:
                    ((Tag) this.objectStack.peek()).setId(Integer.valueOf(value));
                    break;
                case TAG_SLUG:
                    ((Tag) this.objectStack.peek()).setSlug(value);
                    break;
                case TAG_NAME:
                    ((Tag) this.objectStack.peek()).setName(value);
                    break;

                case ITEM_NAME:
                    ((Item) this.objectStack.peek()).setName(value);
                    break;
                case ITEM_STATUS:
                    ((Item) this.objectStack.peek()).setStatus(value);
                    break;
                case ITEM_TYPE:
                    ((Item) this.objectStack.peek()).setItemType(ItemType.valueOf(value.toUpperCase()));
                    break;
                case ITEM_TITLE:
                    ((Item) this.objectStack.peek()).setTitle(value);
                    break;
                case ITEM_AUTHOR:
                    ((Item) this.objectStack.peek()).setAuthor(value);
                    break;
                case ITEM_DESCRIPTION:
                    ((Item) this.objectStack.peek()).setDescription(value);
                    break;
                case ITEM_CONTENT:
                    ((Item) this.objectStack.peek()).setContent(value);
                    break;
                case ITEM_EXCERPT:
                    ((Item) this.objectStack.peek()).setExcerpt(value);
                    break;
                case ITEM_PUBLICATION_DATE_GMT:
                    final ZonedDateTime itemDate = ZonedDateTime.parse(value, dateTimeFormatter.withZone(ZoneId.of(GMT_ZONE_ID)));
                    ((Item) this.objectStack.peek()).setPublicationDateGmt(itemDate);
                    break;
                case ITEM_ATTACHMENT_URL:
                    ((Item) this.objectStack.peek()).setAttachmentUrl(value);
                    break;

                case ITEM_POST_META_KEY:
                    ((PostMeta) this.objectStack.peek()).setKey(value);
                    break;
                case ITEM_POST_META_VALUE:
                    ((PostMeta) this.objectStack.peek()).setValue(value);
                    break;

                case ITEM_COMMENT_ID:
                    ((Comment) this.objectStack.peek()).setId(Integer.parseInt(value));
                    break;
                case ITEM_COMMENT_AUTHOR:
                    ((Comment) this.objectStack.peek()).setAuthor(value);
                    break;
                case ITEM_COMMENT_AUTHOR_EMAIL:
                    ((Comment) this.objectStack.peek()).setAuthorEmail(value);
                    break;
                case ITEM_COMMENT_CONTENT:
                    ((Comment) this.objectStack.peek()).setContent(value);
                    break;
                case ITEM_COMMENT_PUBLICATION_DATE_GMT:
                    final ZonedDateTime commentDate = ZonedDateTime.parse(value, dateTimeFormatter.withZone(ZoneId.of(GMT_ZONE_ID)));
                    ((Comment) this.objectStack.peek()).setPublicationDateGmt(commentDate);
                    break;
                default:
                    // Do nothing for all other attributes
                    break;
            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
        // initialize the root object
        this.website = (Website) objectStack.pop();
        // Populate the parents' categories - if any
        website.updateCategoriesRelationships();
    }

    public Website getWebsite() {
        return website;
    }
}
