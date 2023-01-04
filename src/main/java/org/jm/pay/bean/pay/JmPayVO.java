package org.jm.pay.bean.pay;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author kong
 */
@Data
@Accessors(chain = true)
public class JmPayVO {
    private String response;
    /**
     * 异常原因
     */
    private String err;
}
