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


import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an object that has a name and a map of attributes
 * @version 1.0 - ooxi (2015, inner class of the parser)
 * @version 2.0 - Guillaume Diaz (2021/08 isolation in dedicated class)
 * @author ooxi
 * @author Guillaume Diaz
 */
@Data
public class PhpObject implements Serializable {

    private static final long serialVersionUID = 20210808L;

    private String name;

    private final Map<Object, Object> attributes = new HashMap<>();

    @Override
    public String toString() {
        return "\"" + this.name + "\" : " + this.attributes.toString();
    }
}
