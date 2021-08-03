package eu.daxiongmao.wordpress.wxr.xml;

import eu.daxiongmao.wordpress.wxr.model.Website;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Log4j2
@QuarkusTest
public class WpCliXmlWriterTest {

    @Inject
    private WpCliXmlParser parser;

    @Inject
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
