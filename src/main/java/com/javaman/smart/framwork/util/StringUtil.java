package com.javaman.smart.framwork.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author pengzhe
 * @date 2018/2/26 18:29
 * @description
 */

public class StringUtil {

    public static boolean isEmpty(String string) {
        if (string != null) {
            string = string.trim();
        }
        return StringUtils.isEmpty(string);
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }
}
