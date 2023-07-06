/**
 * Copyright (c) 2007-2008 Zsolt Szász <zsolt@lorecraft.com>
 * 2008-2012 Michele Catalano <michele@catalano.de>
 * 2014-2015 ooxi <violetland@mail.ru>
 * 2021      Daxiongmao.eu (Guillaume Diaz) <guillaume@qin-diaz.com>
 * <p>
 * Original work (ooxi): https://github.com/ooxi/serialized-php-parser
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eu.daxiongmao.utils.php;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Deserializes a serialized PHP data structure into corresponding Java objects.
 * It supports the integer, float, boolean, string primitives that are mapped to
 * their Java equivalent, plus arrays that are parsed into <code>Map</code>
 * instances and objects that are represented by {@link PhpObject} instances.
 * </p>
 *
 * <p>
 * Example of use:
 * </p>
 *
 * <pre>
 * 		String input = "O:8:"TypeName":1:{s:3:"foo";s:3:"bar";}";
 * 		SerializedPhpParser serializedPhpParser = new SerializedPhpParser(input);
 * 	    Object result = serializedPhpParser.parse();
 * </pre>
 *
 * <p>
 * The <code>result</code> object will be a <code>PhpObject</code> with the name
 * "TypeName" and the attribute "foo" = "bar".
 * </p>
 *
 * <p>
 *     Changes 2021: make sure the code works with Java8 (last changed was in 2015) by ooxi,
 *                   add new unit tests and functional validation based on Wordpress database content
 * </p>
 */
public class SerializedPhpParser {

    public String phpSerializeStringToJson(final String input) throws SerializedPhpParserException {
        // Parse DB content to Java Object
        final Object dto = parsePhpSerializeString(input);
        if (dto == null) {
            return "";
        }

        // Cast object to JSON
        return objectToJson(dto, "");
    }

    private String objectToJson(final Object input, final String separator) {
        final StringBuilder json = new StringBuilder();
        if (input instanceof String || input instanceof Integer || input instanceof Double || input instanceof Boolean) {
            // Standard type, just print it out
            json.append(String.format("%s\"%s\"", separator, input));
        } else if (input instanceof Map) {
            // array: use recursive calls
            json.append(String.format("%s{%n", separator));
            final int mapSize = ((Map) input).size();
            int counter = 1;
            for (Map.Entry<Object, Object> entry : ((HashMap<Object, Object>) input).entrySet()) {
                final String key = objectToJson(entry.getKey(), separator + "  ");
                final String value = objectToJson(entry.getValue(), (entry.getValue() instanceof Map ? separator + "  " : ""));
                json.append(String.format("%s: %s%s%n", key, value, (counter < mapSize ? "," : "")));
                counter++;
            }
            json.append(String.format("%s}", separator));
        }
        return json.toString();
    }

    /**
     * <p>To parse an object encoded php_serialize() to Java OBJECT</p>
     * @param input String to process
     * @return result as Java object
     */
    protected Object parsePhpSerializeString(final String input) throws SerializedPhpParserException {
        return parseItem(input).getResult();
    }

    private PhpParseResult parseItem(final String input) throws SerializedPhpParserException {
        if (StringUtils.isBlank(input)) {
            throw new SerializedPhpParserException("No data to extract", SerializedPhpErrorCode.IS_BLANK);
        }

        // Get input type
        final char newType = input.charAt(0);
        final SerializedPhpType type = SerializedPhpType.getType(newType);
        if (type == null) {
            throw new SerializedPhpParserException("Encountered unknown type [" + newType + "]", SerializedPhpErrorCode.UNKNOWN_TYPE);
        }

        String item = null;
        if (input.contains(";")) {
            item = input.substring(0, input.indexOf(';') + 1);
        }

        // Parse
        PhpParseResult result = new PhpParseResult();
        switch (type) {
            case INTEGER:
                result.setResult(parseInt(item));
                result.setProcessTextLength(item.length());
                break;
            case DECIMAL:
                result.setResult(parseFloat(item));
                result.setProcessTextLength(item.length());
                break;
            case BOOLEAN:
                result.setResult(parseBoolean(item));
                result.setProcessTextLength(item.length());
                break;
            case STRING:
                result.setResult(parseString(item));
                result.setProcessTextLength(item.length());
                break;
            case ARRAY:
                result = parseArray(input);
                break;
            default:
                throw new SerializedPhpParserException("Encountered unsupported type [" + type + "], cannot extract data", SerializedPhpErrorCode.UNKNOWN_TYPE);
        }
        return result;
    }

    /**
     * <p>To parse an Array encoded with php_serialize()</p>
     * <p>It assumes that the Array is always a key-pair.
     * <ul>
     *     <li>"key" can be integer (index value) or String (property name)</li>
     *     <li>"value" can be anything: integer, boolean, decimal, string</li>
     * </ul>
     * The array is always encoded like:
     * <pre>
     *     a:2:{s:4:"name";s:10:"Subscriber";s:12:"capabilities";}
     * </pre>
     * (i) an array can contains inner array(s) as well
     * </p>
     * @param input String to process
     * @return result
     */
    protected PhpParseResult parseArray(final String input) throws SerializedPhpParserException {
        // extract array length
        String content = input.substring(input.indexOf(':') + 1);
        String sizeStr = content.substring(0, content.indexOf(':'));
        int size = Integer.parseInt(sizeStr);
        content = content.substring(content.indexOf(':') + 1);

        // Array must start with '{'
        if (!content.startsWith("{")) {
            throw new SerializedPhpParserException("Unexpected start of serialized Array, missing { |input value: " + input, SerializedPhpErrorCode.MISSING_STARTING_STRING);
        }
        content = content.substring(content.indexOf('{') + 1);

        // Store processed size, for caller. a:12:{
        int processedStringLength = 2 + sizeStr.length() + 2;

        final Map<Object, Object> result = new HashMap<>();
        for (int i = 0; i < size; i++) {
            // It is always a pair of "key", "value"
            //     "key" can be integer (index value) or String (property)
            // --- Key
            final PhpParseResult key = parseItem(content);
            content = content.substring(key.getProcessTextLength());
            processedStringLength += key.getProcessTextLength();
            // --- Value
            final PhpParseResult value = parseItem(content);
            content = content.substring(value.getProcessTextLength());
            processedStringLength += value.getProcessTextLength();
            // --- Save
            result.put(key.getResult(), value.getResult());
        }


        // Array must end with '}'
        if (!content.endsWith("}")) {
            throw new SerializedPhpParserException("Unexpected end of serialized Array, missing } |input value: " + input, SerializedPhpErrorCode.MISSING_CLOSER_STRING);
        }
        // return result
        return new PhpParseResult(processedStringLength + 1, result);
    }

    /**
     * <p>To parse a String encoded with php_serialize()</p>
     * <p>It assumes strings are utf8 encoded.<br>
     * The encoding length depends on the UTF-8 specifications: https://datatracker.ietf.org/doc/html/rfc3629#section-3
     * <pre>
     * Char. number range  |        UTF-8 octet sequence
     *    (hexadecimal)    |              (binary)
     * --------------------+---------------------------------------------
     * 0000 0000-0000 007F | 0xxxxxxx
     * 0000 0080-0000 07FF | 110xxxxx 10xxxxxx
     * 0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx
     * 0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
     * </pre>
     * </p>
     * @param input String to process. Format must be <code>s:text_length:"text value";</code><br>
     *              Examples:
     *              <ul>
     *                  <li>s:4:"name";</li>
     *                  <li>s:25:"backwpup_backups_download";</li>
     *              </ul>
     * @return String value
     */
    protected String parseString(final String input) throws SerializedPhpParserException {
        // arg check
        String strValue = getValue(input, SerializedPhpType.STRING);
        // contain 2 '\"', end with '";'
        if (strValue.chars().filter(ch -> ch == '\"').count() != 2) {
            throw new SerializedPhpParserException("Cannot process String: input is not conform. It must start with contain 2 \" | Incorrect input= " + input, SerializedPhpErrorCode.MISSING_DELIMITER_STRING);
        }

        // Extract text
        return strValue.substring(1, strValue.length() - 1);
    }


    /**
     * <p>To parse a Boolean encoded with php_serialize()</p>
     * @param input boolean to process. Format must be <code>b:0;</code> or <code>b:1;</code>
     * @return boolean result
     */
    protected Boolean parseBoolean(final String input) throws SerializedPhpParserException {
        final String value = getValue(input, SerializedPhpType.BOOLEAN);
        if ("1".equals(value) || "true".equals(value)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <p>To parse an Integer encoded with php_serialize()</p>
     * @param input integer to process. Format must be <code>i:number;</code></code>
     * @return integer result
     */
    protected Integer parseInt(final String input) throws SerializedPhpParserException {
        final String value = getValue(input, SerializedPhpType.INTEGER);
        return Integer.parseInt(value);
    }

    /**
     * <p>To parse a decimal (float) encoded with php_serialize()</p>
     * @param input float to process. Format must be <code>d:number;</code></code>
     * @return integer result
     */
    protected Double parseFloat(final String input) throws SerializedPhpParserException {
        final String value = getValue(input, SerializedPhpType.DECIMAL);
        return Double.parseDouble(value);
    }

    /**
     * <p>To parse a specific value encoded with php_serialize()</p>
     * @param input string to process
     * @param type data type to handle
     * @return result as a string
     */
    private String getValue(final String input, final SerializedPhpType type) throws SerializedPhpParserException {
        // arg check
        if (StringUtils.isBlank(input)) {
            throw new SerializedPhpParserException("Cannot process " + type.name() + ": no input.", SerializedPhpErrorCode.IS_BLANK);
        }
        // It must start with '{idbs}:' and end with ';'
        if (!input.startsWith(type.getSerializeKey() + ":")) {
            throw new SerializedPhpParserException("Cannot process "+ type.name() +": input is not conform. It must start with '" + type.getSerializeKey() + "' | Incorrect input= " + input, SerializedPhpErrorCode.INCORRECT_FORMAT);
        }
        if (!input.endsWith(";")) {
            throw new SerializedPhpParserException("Cannot process "+ type.name() +": input is not conform. It must end with ';' | Incorrect input= " + input, SerializedPhpErrorCode.MISSING_DELIMITER_STRING);
        }

        // extract value
        String value = input.substring(input.indexOf(":") + 1, input.lastIndexOf(";"));
        if (SerializedPhpType.STRING == type) {
            // Iterate again for string due to string length
            value = value.substring(value.indexOf(":") + 1);
        }
        if (StringUtils.isBlank(value)) {
            throw new SerializedPhpParserException("Cannot process "+ type.name() + ": input is not conform. No value provided | Incorrect input= " + input, SerializedPhpErrorCode.INCORRECT_FORMAT);
        }
        return value;
    }
}
