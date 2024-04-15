package org.jm.pay.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * 数值拷贝
 *
 * @author kong
 */
@Slf4j
public class BeanTools {


    /**
     * 转换对象并复制
     */
    public static <T> T map(Object source, Class<T> target) {
        if (null == source) {
            return null;
        }
        T t = BeanUtils.instantiateClass(target);
        BeanUtils.copyProperties(source, t);
        return t;
    }

}
