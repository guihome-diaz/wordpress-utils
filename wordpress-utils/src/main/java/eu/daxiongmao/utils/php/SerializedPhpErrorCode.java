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
 * Exception to signal a problem while parsing PHP "serialized" object value into Java
 * @author 2007-2008 # Zsolt Szász <zsolt@lorecraft.com>
 * @author 2008-2012 # Michele Catalano <michele@catalano.de>
 * @author 2014-2015 # ooxi <violetland@mail.ru>
 * @author 2021 # Daxiongmao.eu (Guillaume Diaz) <guillaume@qin-diaz.com> - Extraction in dedicated class
 * @version 4
 */
public enum SerializedPhpErrorCode {

    /** Given item is blank and not conform. */
    IS_BLANK(0),

    /**
     * code if serialized String is too Long (example s:5;"Hello Next"; )
     */
    TOO_LONG_STRING(1),

    /**
     * code if serialized string is too short
     * (example: input string is latin1 with special char but assumeUTF8 is set true)
     */
    TOO_SHORT_STRING(2),

    /**
     * code if serialized reference is pointing to a value out of the reference index.
     * (example: a:3:{i:1;i:20;i:2;R:4;i:3;s:5;"Hello";} )
     */
    OUT_OF_RANG_REFERENCE(3),

    /**
     * code if serialized array missing closer char }.
     */
    MISSING_CLOSER_STRING(4),

    /**
     * code if serialized value missing delimiter ( : or ; )
     */
    MISSING_DELIMITER_STRING(5),

    /**
     * code if serialized string is too short (example: s:8;"Hello"; )
     */
    TOO_SHORT_INPUT_STRING(6),

    /** The given structure does not respect the php_serialize() format */
    INCORRECT_FORMAT(7),

    /**
     * code if serialized string has a unknown type used
     * @see SerializedPhpType for known types
     * (known types: i, d, b, s, a, O, N, R ).
     */
    UNKNOWN_TYPE(9),

    /**
     * code if serialized array missing starting char {.
     */
    MISSING_STARTING_STRING(10),

    /**
     * code if serialized string failed with unknown reason
     */
    UNEXPECTED_FAILURE(99);

    private int errorCode;

    SerializedPhpErrorCode(final int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return error code for this particular error cause
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode error code
     * @return error reason for the given error code
     */
    public SerializedPhpErrorCode fromErrorCode(final int errorCode) {
        SerializedPhpErrorCode result = UNEXPECTED_FAILURE;
        for (SerializedPhpErrorCode item : values()) {
            if (item.errorCode == errorCode) {
                result = item;
                break;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s [errCode: %s]", this.name(), this.errorCode);
    }
}
