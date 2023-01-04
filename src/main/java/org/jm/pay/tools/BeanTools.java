package org.jm.pay.tools;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * 数值拷贝
 *
 * @author kong
 */
@Slf4j
public class BeanTools {

    /**
     * 通过发现差异合并两个bean
     */
    public static <T> void merge(T source, T target) {
        Class<?> clazz = target.getClass();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            // Iterate over all the attributes
            Stream.of(beanInfo.getPropertyDescriptors()).forEach(descriptor -> {

                // Only copy writable attributes
                if (descriptor.getWriteMethod() != null) {
                    try {
                        Object originalValue = descriptor.getReadMethod().invoke(target);
                        // Only copy values values where the target values is null
                        if (originalValue == null) {
                            Object defaultValue = descriptor.getReadMethod().invoke(source);
                            descriptor.getWriteMethod().invoke(target, defaultValue);
                        }
                    } catch (Exception e) {
                        log.error("", e);
//                        throw new GeneralException("merge class: " + clazz + " failed!");
                    }
                }
            });
        } catch (Exception e) {
            log.error("", e);
//            throw new GeneralException("merge class: " + clazz + " failed!");
        }
    }


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

    /**
     * 复制对象属性到另外一个对象
     */
    public static Object copy(Object source, Object target) {
        if (source == null || target == null) {
            return target;
        }
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 基于Dozer转换Collection中对象的类型.
     */
    public static <T> List<T> mapList(Collection<?> sourceList, Class<T> target) {
        if (sourceList == null) {
            return null;
        }
        List<T> destinationList = Lists.newArrayList();
        for (Object sourceObject : sourceList) {
            T newObj = map(sourceObject, target);
            destinationList.add(newObj);
        }
        return destinationList;
    }


    /**
     * 拷贝忽略指定的属性集
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        if (null == source || null == target) {
            return;
        }
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * 转换List source to List target
     */
    public static <A, B> List<B> convertList(List<A> source, Class<B> clazz) {
        List<B> list = new ArrayList<>(source.size());
        if (CollectionUtils.isEmpty(source)) {
            return list;
        }
        source.forEach(item -> list.add(map(item, clazz)));
        return list;
    }
}
