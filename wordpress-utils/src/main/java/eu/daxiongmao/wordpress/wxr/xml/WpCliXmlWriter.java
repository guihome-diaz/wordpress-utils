package eu.daxiongmao.wordpress.wxr.xml;

import eu.daxiongmao.wordpress.wxr.WpCliXmlTag;
import eu.daxiongmao.wordpress.wxr.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * To generate a WordPress export file in the same format as wp-cli (WXR format)
 * @author Guillaume Diaz
 * @version 1.0 (2020/12)
 * @since 2020/12
 */
@Log4j2
@ApplicationScoped
public class WpCliXmlWriter {

    private final String WXR_VERSION = "1.2";

    /**
     * To generate a WordPress export file in the same format as WP-cli (WXR format)
     * @param outputFile complete path to save the file
     * @param website data object to write
     * @return boolean, "true" if the operation succeed
     */
    public boolean writeWxrFile(final Path outputFile, final Website website) {
        // Arg check
        if (website == null) {
            log.warn("Cannot generate WordPress export file (WXR): no data provided");
            return false;
        }

        // Generate XML
        final Document xmlFile = buildXml(website);

        // Write XML
        return writeXml(outputFile, xmlFile);
    }

    private Document buildXml(final Website website) {
        try {
            log.debug("XML generation start");
            final DocumentBuilderFactory xmlDocumentFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = xmlDocumentFactory.newDocumentBuilder();
            final Document doc = dBuilder.newDocument();
            // Root element: tag RSS
            final Element rootElement = doc.createElement("rss");
            rootElement.setAttribute("xmlns:content", "http://purl.org/rss/1.0/modules/content/");
            rootElement.setAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/");
            rootElement.setAttribute("xmlns:wp", "http://wordpress.org/export/1.2/");
            rootElement.setAttribute("xmlns:excerpt", "http://wordpress.org/export/1.2/excerpt/");
            doc.appendChild(rootElement);
            // XML content
            final Element channel = getChannelElement(doc, website);
            rootElement.appendChild(channel);
            log.debug("XML generation complete with success");
            return doc;
        } catch (Exception e) {
            log.error("XML generation failure due to unknown error", e);
            return null;
        }
    }

    private Element getChannelElement(final Document doc, final Website website) {
        // Root element
        final Element channel = doc.createElement(WpCliXmlTag.WEBSITE.getXmlTag());
        populateChannelElement(doc, channel, website);
        // authors
        website.getAuthors().forEach((author -> { channel.appendChild(createAuthorTag(doc, author)); }));
        // categories
        website.getCategories().values().forEach((category -> { channel.appendChild(createCategoryTag(doc, category)); }));
        // tags
        website.getTags().forEach((tag -> { channel.appendChild(createTag(doc, tag)); }));
        // items
        website.getItems().forEach((item -> { channel.appendChild(createItemTag(doc, item)); }));

        return channel;
    }

    private void populateChannelElement(final Document doc, final Element channelElement, final Website website) {
        addStandaloneElement(doc, channelElement, WpCliXmlTag.WEBSITE_TITLE.getXmlTag(), website.getTitle());
        addStandaloneElement(doc, channelElement, WpCliXmlTag.WEBSITE_URL.getXmlTag(), website.getUrl());
        addStandaloneElement(doc, channelElement, WpCliXmlTag.WEBSITE_DESCRIPTION.getXmlTag(), website.getDescription());
        final LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        final String publicationTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        addStandaloneElement(doc, channelElement, WpCliXmlTag.WEBSITE_PUBLICATION_DATE.getXmlTag(), publicationTime);
        addStandaloneElement(doc, channelElement, WpCliXmlTag.WEBSITE_LANGUAGE.getXmlTag(), website.getLanguage());
        addStandaloneElement(doc, channelElement, WpCliXmlTag.WEBSITE_WXR_VERSION.getXmlTag(), WXR_VERSION);
        addStandaloneElement(doc, channelElement, WpCliXmlTag.WEBSITE_WORDPRESS_BASE_SITE_URL.getXmlTag(), website.getWordpressBaseUrl());
        addStandaloneElement(doc, channelElement, WpCliXmlTag.WEBSITE_WORDPRESS_BASE_BLOG_URL.getXmlTag(), website.getWordpressBaseUrl());
    }

    private Element createAuthorTag(final Document doc, final Author author) {
        final Element authorElement = doc.createElement(WpCliXmlTag.AUTHOR.getXmlTag());
        addStandaloneElement(doc, authorElement, WpCliXmlTag.AUTHOR_ID.getXmlTag(), (author.getId() != null ? Integer.toString(author.getId()) : ""));
        addStandaloneElement(doc, authorElement, WpCliXmlTag.AUTHOR_LOGIN.getXmlTag(), author.getLogin());
        addStandaloneElement(doc, authorElement, WpCliXmlTag.AUTHOR_EMAIL.getXmlTag(), author.getEmail());
        addStandaloneElement(doc, authorElement, WpCliXmlTag.AUTHOR_DISPLAY_NAME.getXmlTag(), author.getDisplayName());
        addStandaloneElement(doc, authorElement, WpCliXmlTag.AUTHOR_FIRST_NAME.getXmlTag(), author.getFirstName());
        addStandaloneElement(doc, authorElement, WpCliXmlTag.AUTHOR_LAST_NAME.getXmlTag(), author.getLastName());
        return authorElement;
    }

    private Element createCategoryTag(final Document doc, final Category category) {
        final Element categoryElement = doc.createElement(WpCliXmlTag.CATEGORY.getXmlTag());
        addStandaloneElement(doc, categoryElement, WpCliXmlTag.CATEGORY_ID.getXmlTag(), (category.getId() != null ? Integer.toString(category.getId()) : ""));
        addStandaloneElement(doc, categoryElement, WpCliXmlTag.CATEGORY_NAME.getXmlTag(), category.getName());
        addStandaloneElement(doc, categoryElement, WpCliXmlTag.CATEGORY_PARENT.getXmlTag(), category.getParentCategorySlug());
        addStandaloneElement(doc, categoryElement, WpCliXmlTag.CATEGORY_SLUG.getXmlTag(), category.getSlug());
        return categoryElement;
    }

    private Element createTag(final Document doc, final Tag tag) {
        final Element tagElement = doc.createElement(WpCliXmlTag.TAG.getXmlTag());
        addStandaloneElement(doc, tagElement, WpCliXmlTag.TAG_ID.getXmlTag(), (tag.getId() != null ? Integer.toString(tag.getId()) : ""));
        addStandaloneElement(doc, tagElement, WpCliXmlTag.TAG_SLUG.getXmlTag(), tag.getSlug());
        addStandaloneElement(doc, tagElement, WpCliXmlTag.TAG_NAME.getXmlTag(), tag.getName());
        return tagElement;
    }

    private Element createItemTag(final Document doc, final Item item) {
        // cf example line 3877..
        final Element itemElement = doc.createElement(WpCliXmlTag.ITEM.getXmlTag());
        // FIXME
        addStandaloneElement(doc, itemElement, WpCliXmlTag.ITEM_TITLE.getXmlTag(), item.getTitle());
        // TODO add link and publication date
        addStandaloneElement(doc, itemElement, WpCliXmlTag.ITEM_AUTHOR.getXmlTag(), item.getAuthor());
        addStandaloneElement(doc, itemElement, WpCliXmlTag.ITEM_DESCRIPTION.getXmlTag(), item.getDescription());
        addStandaloneElement(doc, itemElement, WpCliXmlTag.ITEM_CONTENT.getXmlTag(), item.getContent());
        addStandaloneElement(doc, itemElement, WpCliXmlTag.ITEM_EXCERPT.getXmlTag(), item.getExcerpt());
        // TODO to be continued..
        return itemElement;
    }

    private void addStandaloneElement(final Document doc, final Element parentElement, final String name, final String value) {
        final Element node = doc.createElement(name);
        if (StringUtils.isNotBlank(value)) {
            node.appendChild(doc.createTextNode(value));
        }
        parentElement.appendChild(node);
    }

    private boolean writeXml(final Path outputFile, final Document xmlFile) {
        // arg check. Skip this step if XML document generation has failed
        if (xmlFile == null) {
            return false;
        }

        log.debug("XML output start");
        try {
            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            final DOMSource source = new DOMSource(xmlFile);

            // Output to console
            final StreamResult console = new StreamResult(System.out);
            transformer.transform(source, console);
            log.info("XML output to console is complete");

            // Write to file
            if (outputFile != null) {
                final StreamResult file = new StreamResult(outputFile.toFile());
                transformer.transform(source, file);
                log.info("XML output to file is complete");
            }
            return true;
        } catch (Exception e) {
            log.error("XML output failure due to unknown error", e);
            return false;
        }
    }


}
