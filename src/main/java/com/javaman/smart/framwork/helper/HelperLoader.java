package com.javaman.smart.framwork.helper;

import com.javaman.smart.framwork.util.ClassUtil;

/**
 * @author pengzhe
 * @date 2018/2/27 10:44
 * @description 加载相应的Helper类
 */

public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class};
        for (Class<?> clazz : classList) {
            ClassUtil.loadClass(clazz.getName(), false);
        }
    }
}
