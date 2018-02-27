package com.javaman.smart.framwork.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author pengzhe
 * @date 2018/2/27 10:19
 * @description
 */

public final class ArrayUtil {

    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isEmpty(array);
    }

    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }
}
