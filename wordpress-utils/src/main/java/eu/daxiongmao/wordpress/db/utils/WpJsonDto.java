package eu.daxiongmao.wordpress.db.utils;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class WpJsonDto implements Serializable, Comparable<WpJsonDto> {

    /** Name of the current property (optional). */
    private String key;
    /** Value as a String. */
    private String stringValue;
    /** Current array index (optional). */
    private Integer index;
    /** Value as an Array of items */
    private List<WpJsonDto> items;

    @Override
    public int compareTo(WpJsonDto other) {
        if (other == null) {
            return 1;
        }
        // compare key, if available
        int result = 0;
        if (StringUtils.isNotBlank(this.key)) {
            if (StringUtils.isBlank(other.key)) {
                return 1;
            } else {
                result = this.key.compareTo(other.key);
                if (result != 0) { return result; }
            }
        } else if (StringUtils.isNotBlank(other.key)) {
            return -1;
        }
        // Compare index, if available
        if (this.index != null) {
            if (other.index == null) {
                return 1;
            } else {
                result = this.index.compareTo(other.index);
                if (result != 0) { return result; }
            }
        } else if (other.index != null) {
            return -1;
        }
        // Compare value, if available
        if (StringUtils.isNotBlank(this.stringValue)) {
            if (StringUtils.isBlank(other.stringValue)) {
                return 1;
            } else {
                return this.stringValue.compareTo(other.stringValue);
            }
        } else if (StringUtils.isNotBlank(other.stringValue)) {
            return -1;
        }
        // equality
        return 0;
    }

    public String toString() {
        final StringBuilder indentation = new StringBuilder();
        final StringBuilder result = new StringBuilder();
        if (StringUtils.isNotBlank(this.key)) {
            result.append(String.format("\"%s%s\" : ", indentation, this.stringValue));
        }
        if (StringUtils.isNotBlank(this.stringValue)) {
            result.append(String.format("\"%s\"", indentation, this.stringValue));
        }
        if (items != null && !items.isEmpty()) {
            // sort content
            Collections.sort(items);
            // Append
            result.append("[");
            for (WpJsonDto item : items) {
                result.append(items.toString());
            }
            result.append("]");
        }
        return result.toString();
    }


}
