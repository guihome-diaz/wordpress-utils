package eu.daxiongmao.utils.php;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Map;

public class SerializedPhpParserTest {

    @ParameterizedTest(name = "parse String - {0}")
    @CsvFileSource(resources = "/eu/daxiongmao/utils/php/parseString.csv", numLinesToSkip = 0)
    public void parseString(final String testString, final String expectedText) throws SerializedPhpParserException {
        final SerializedPhpParser parser = new SerializedPhpParser();
        Assertions.assertEquals(expectedText, parser.parseString(testString));
    }

    @Test
    public void parseBoolean() throws SerializedPhpParserException {
        final SerializedPhpParser parser = new SerializedPhpParser();
        Assertions.assertFalse(parser.parseBoolean("b:0;"));
        Assertions.assertTrue(parser.parseBoolean("b:1;"));
        Assertions.assertTrue(parser.parseBoolean("b:true;"));

        try {
            parser.parseBoolean(" ");
        } catch (SerializedPhpParserException e) {
            Assertions.assertEquals(SerializedPhpErrorCode.IS_BLANK, e.errorReason);
        }
        try {
            parser.parseBoolean("b:");
        } catch (SerializedPhpParserException e) {
            Assertions.assertEquals(SerializedPhpErrorCode.MISSING_DELIMITER_STRING, e.errorReason);
        }
    }

    @Test
    public void parseInteger() throws SerializedPhpParserException {
        final SerializedPhpParser parser = new SerializedPhpParser();
        Assertions.assertEquals(0, parser.parseInt("i:0;"));
        Assertions.assertEquals(-1523, parser.parseInt("i:-1523;"));
        Assertions.assertEquals(1231456469, parser.parseInt("i:1231456469;"));
        Assertions.assertEquals(1, parser.parseInt("i:001;"));

        try {
            parser.parseInt(" ");
        } catch (SerializedPhpParserException e) {
            Assertions.assertEquals(SerializedPhpErrorCode.IS_BLANK, e.errorReason);
        }
        try {
            parser.parseInt("i:;");
        } catch (SerializedPhpParserException e) {
            Assertions.assertEquals(SerializedPhpErrorCode.INCORRECT_FORMAT, e.errorReason);
        }
    }

    @Test
    public void parseDouble() throws SerializedPhpParserException {
        final SerializedPhpParser parser = new SerializedPhpParser();
        Assertions.assertEquals(0.5, parser.parseFloat("d:0.5;"));
        Assertions.assertEquals(-1234.5689, parser.parseFloat("d:-1234.5689;"));
        Assertions.assertEquals(1111111, parser.parseFloat("d:1111111;"));
        Assertions.assertEquals(1, parser.parseFloat("d:001;"));
        Assertions.assertEquals(.000001, parser.parseFloat("d:000.000001;"));
        Assertions.assertEquals(0.00, parser.parseFloat("d:0;"));
    }

    @Test
    public void parseSimpleArray() throws SerializedPhpParserException {
        final SerializedPhpParser parser = new SerializedPhpParser();
        final String input = "a:2:{s:4:\"name\";s:10:\"Subscriber\";s:7:\"is_test\";b:1;}";

        PhpParseResult result = parser.parseArray(input);
        Assertions.assertNotNull(result.getResult());
        Assertions.assertFalse(((Map<Object,Object>) result.getResult()).isEmpty());
        Assertions.assertEquals(2, ((Map<Object,Object>) result.getResult()).size());

        Assertions.assertNotNull(((Map<Object,Object>) result.getResult()).get("name"));
        Assertions.assertEquals("Subscriber", ((Map<Object,Object>) result.getResult()).get("name"));
        Assertions.assertNotNull(((Map<Object,Object>) result.getResult()).get("is_test"));
        Assertions.assertEquals(true, ((Map<Object,Object>) result.getResult()).get("is_test"));
    }

    @Test
    public void parseSimpleArrayToJson() throws SerializedPhpParserException {
        final SerializedPhpParser parser = new SerializedPhpParser();
        final String input = "a:2:{s:4:\"name\";s:10:\"Subscriber\";s:7:\"is_test\";b:1;}";

        String json = parser.phpSerializeStringToJson(input);
        Assertions.assertNotNull(json);
        System.out.println(json);

        String expectation = String.format("{%n  \"name\": \"Subscriber\",%n  \"is_test\": \"true\"%n}");
        Assertions.assertEquals(expectation, json);
    }

    @Test
    public void parseAdvancedArray() throws SerializedPhpParserException {
        final SerializedPhpParser parser = new SerializedPhpParser();
        final String input = "a:2:{s:4:\"name\";s:10:\"Subscriber\";s:12:\"capabilities\";a:3:{s:4:\"read\";b:1;s:7:\"level_0\";b:1;s:24:\"NextGEN Gallery overview\";b:0;}}";

        PhpParseResult result = parser.parseArray(input);
        Assertions.assertNotNull(result.getResult());
        Assertions.assertFalse(((Map<Object,Object>) result.getResult()).isEmpty());
        Assertions.assertEquals(2, ((Map<Object,Object>) result.getResult()).size());
        Assertions.assertNotNull(((Map<Object,Object>) result.getResult()).get("name"));
        Assertions.assertEquals("Subscriber", ((Map<Object,Object>) result.getResult()).get("name"));
        Assertions.assertNotNull(((Map<Object,Object>) result.getResult()).get("capabilities"));

        Assertions.assertFalse(((Map<Object,Object>)(((Map<Object,Object>) result.getResult()).get("capabilities"))).isEmpty());
        Assertions.assertEquals(3, ((Map<Object,Object>)(((Map<Object,Object>) result.getResult()).get("capabilities"))).size());
        Assertions.assertEquals(true, ((Map<Object,Object>)(((Map<Object,Object>) result.getResult()).get("capabilities"))).get("read"));
        Assertions.assertEquals(true, ((Map<Object,Object>)(((Map<Object,Object>) result.getResult()).get("capabilities"))).get("level_0"));
        Assertions.assertEquals(false, ((Map<Object,Object>)(((Map<Object,Object>) result.getResult()).get("capabilities"))).get("NextGEN Gallery overview"));
    }

    @Test
    public void parseAdvancedArrayToJson() throws SerializedPhpParserException {
        final SerializedPhpParser parser = new SerializedPhpParser();
        final String input = "a:2:{s:4:\"name\";s:10:\"Subscriber\";s:12:\"capabilities\";a:3:{s:4:\"read\";b:1;s:7:\"level_0\";b:1;s:24:\"NextGEN Gallery overview\";b:0;}}";

        String json = parser.phpSerializeStringToJson(input);
        Assertions.assertNotNull(json);
        System.out.println(json);

        String expectation = String.format("{%n  \"capabilities\":   {" +
                "%n    \"read\": \"true\",%n    \"NextGEN Gallery overview\": \"false\",%n    \"level_0\": \"true\"%n" +
                "  },%n  \"name\": \"Subscriber\"%n}");
        Assertions.assertEquals(expectation, json);
    }

}

