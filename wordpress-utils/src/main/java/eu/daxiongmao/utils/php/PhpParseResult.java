package eu.daxiongmao.utils.php;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class PhpParseResult implements Serializable {

    private static final long serialVersionUID = 20210808L;

    @EqualsAndHashCode.Include
    private int processTextLength;
    @EqualsAndHashCode.Include
    private Object result;

    public PhpParseResult() {}

    public PhpParseResult(int processTextLength, Object result) {
        this.result = result;
        this.processTextLength = processTextLength;
    }

}
