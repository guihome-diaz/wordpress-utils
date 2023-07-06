/**
 * Copyright (c) 2007-2008 Zsolt Szász <zsolt@lorecraft.com>
 *               2008-2012 Michele Catalano <michele@catalano.de>
 *               2014-2015 ooxi <violetland@mail.ru>
 *               2021      Daxiongmao.eu (Guillaume Diaz) <guillaume@qin-diaz.com>
 *
 * Original work (ooxi): https://github.com/ooxi/serialized-php-parser
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eu.daxiongmao.utils.php;

/**
 * List of data type supported by PHP "serialized".
 * @author 2007-2008 # Zsolt Szász <zsolt@lorecraft.com>
 * @author 2008-2012 # Michele Catalano <michele@catalano.de>
 * @author 2014-2015 # ooxi <violetland@mail.ru>
 * @author 2021 # Daxiongmao.eu (Guillaume Diaz) <guillaume@qin-diaz.com> - Extraction in dedicated class
 * @version 4
 */
public enum SerializedPhpType {

    /** array index # i:integer_value */
    INTEGER('i'),

    /** Decimal value (float) */
    DECIMAL('d'),

    /** Boolean value. (0 = false, 1 = true) */
    BOOLEAN('b'),

    /** String value. s:"text length":"text value" */
    STRING('s'),

    /** Array # a:"array size":{ .. } */
    ARRAY('a'),

    /** Data object */
    OBJECT('O'),

    /** NULL entry */
    NULL('N'),

    /** Reference to something else */
    REFERENCE('R');

    private char serializeKey;

    SerializedPhpType(char serializeKey) {
        this.serializeKey = serializeKey;
    }

    public char getSerializeKey() {
        return serializeKey;
    }

    public static SerializedPhpType getType(final char inputChar) {
        SerializedPhpType result = null;
        for (SerializedPhpType type : values()) {
            if (type.serializeKey == inputChar) {
                result = type;
                break;
            }
        }
        return result;
    }
}
