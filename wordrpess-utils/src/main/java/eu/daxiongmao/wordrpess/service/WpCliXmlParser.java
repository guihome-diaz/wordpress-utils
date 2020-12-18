package eu.daxiongmao.wordrpess.service;

import eu.daxiongmao.wordrpess.model.Website;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * To parse a Wordpress WP-CLI export file
 * @author Guillaume Diaz
 * @version 1.0 (2020/12)
 * @since 2020/12
 */
@Log4j2
@Component
public class WpCliXmlParser {

    public Optional<Website> parseWpCliExportFile(Path wpCliXmlFile) {
        // args check
        if (wpCliXmlFile == null) {
            throw new IllegalArgumentException("You must proved a WP-CLI export XML file to parse");
        }
        if (Files.notExists(wpCliXmlFile)) {
            throw new IllegalArgumentException("Cannot parse requested WP-CLI export XML file because it does not exists. Check the path: " + wpCliXmlFile.toString());
        }

        try {
            // Create XML SAX parser
            // This will populate the output content based on file stream.
            // i.e: it does not load the whole document in memory, that's why it produces better performances
            final SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            parserFactory.setValidating(true);
            final SAXParser parser = parserFactory.newSAXParser();

            // Parse XML with dedicated handler
            final WpCliXmlHandler wpCliXmlHandler = new WpCliXmlHandler();
            parser.parse(wpCliXmlFile.toFile(), wpCliXmlHandler);
            return Optional.ofNullable(wpCliXmlHandler.getWebsite());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
