package eu.daxiongmao.wordpress.wxr.xml;

import eu.daxiongmao.wordpress.wxr.model.Website;
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
public class WpCliXmlWriterTest {

    @Autowired
    private WpCliXmlParser parser;

    @Autowired
    private WpCliXmlWriter writer;

    @Test
    public void writeXml() {
        // 1. parse XML
        final Path xmlTestFile = Paths.get("src", "test", "resources", "wp-cli", "wxr_exports", "wp-cli_wxr_test_export.xml");
        Assertions.assertTrue(Files.exists(xmlTestFile));
        final Optional<Website> website = parser.parseWpCliExportFile(xmlTestFile);
        Assertions.assertTrue(website.isPresent());

        // 2. Write again XML
        final boolean generationResult = writer.writeWxrFile(null, website.get());
        Assertions.assertTrue(generationResult);
    }
}
