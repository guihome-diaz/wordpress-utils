package eu.daxiongmao.wordrpess.service;

import eu.daxiongmao.wordrpess.model.Website;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Log4j2
@SpringBootTest
public class WpCliXmlParserTest {

    @Autowired
    private WpCliXmlParser parser;

    @Test
    public void parseXml() {
        final Path xmlTestFile = Paths.get("src", "test", "resources", "wp-cli", "wxr_exports", "wp-cli_wxr_test_export.xml");
        Assertions.assertTrue(Files.exists(xmlTestFile));

        Optional<Website> website = parser.parseWpCliExportFile(xmlTestFile);
        Assertions.assertTrue(website.isPresent());
        Assertions.assertEquals(2, website.get().getAuthors().size());
        Assertions.assertEquals(96, website.get().getCategories().size());
        Assertions.assertEquals(646, website.get().getTags().size());
        Assertions.assertEquals(15, website.get().getItems().size());
        log.info("{} authors, {} categories, {} tags, {} items", website.get().getAuthors().size(), website.get().getCategories().size(), website.get().getTags().size(), website.get().getItems().size());
    }

}
