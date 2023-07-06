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
 * @author 2021 # Daxiongmao.eu (Guillaume Diaz) <guillaume@qin-diaz.com>
 * @version 4
 */
public class SerializedPhpParserException extends Exception {

    private static final long serialVersionUID = 20210808L;

    /** [optional, can be NULL] position (char index in given String) where the error occurred */
    public int position = 0;

    /** [optional, can be NULL] error cause */
    public SerializedPhpErrorCode errorReason = null;

    public SerializedPhpParserException() {
        super();
    }

    /**
     * @param message error message
     */
    public SerializedPhpParserException(String message) {
        super(message);
    }

    /**
     * @param message error message
     * @param errorReason error reason
     */
    public SerializedPhpParserException(String message, SerializedPhpErrorCode errorReason) {
        super(message + " # Error: " + errorReason);
        this.errorReason = errorReason;
    }

    /**
     * @param message error message
     * @param position position (char index in given String) where the error occurred
     */
    public SerializedPhpParserException(String message, int position) {
        super(message + " # Error occurred at String Position: " + position);
        this.position = position;
    }

    /**
     * @param message error message
     * @param position position (char index in given String) where the error occurred
     * @param cause related exception
     */
    public SerializedPhpParserException(String message, int position, Throwable cause) {
        super(message + "# Error occurred at String Position: " + position, cause);
        this.position = position;
    }

    /**
     * @param message error message
     * @param position position (char index in given String) where the error occurred
     * @param errorReason error reason
     */
    public SerializedPhpParserException(String message, int position, SerializedPhpErrorCode errorReason) {
        super(message + " # Error [" + errorReason + "] String Position: " + position);
        this.position = position;
        this.errorReason = errorReason;
    }

    /**
     * @param message error message
     * @param position position (char index in given String) where the error occurred
     * @param errorReason error reason
     * @param cause related exception
     */
    public SerializedPhpParserException(String message, int position, SerializedPhpErrorCode errorReason, Throwable cause) {
        super(message + " # Error [" + errorReason + "] String Position: " + position, cause);
        this.position = position;
        this.errorReason = errorReason;
    }

    /**
     * @param message error message
     * @param cause related exception
     */
    public SerializedPhpParserException(String message, Throwable cause) {
        super(message, cause);
    }

}
