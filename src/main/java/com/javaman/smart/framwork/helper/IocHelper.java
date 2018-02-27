package com.javaman.smart.framwork.helper;

import com.javaman.smart.framwork.annotation.Inject;
import com.javaman.smart.framwork.util.ArrayUtil;
import com.javaman.smart.framwork.util.CollectionUtil;
import com.javaman.smart.framwork.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author pengzhe
 * @date 2018/2/27 10:06
 * @description 依赖注入助手类
 */

public class IocHelper {

    static {
        /**
         * 获取所有的Bean类与Bean实例之间的映射关系,简称BeanMap
         */
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            //遍历beanMap
            for (Map.Entry<Class<?>, Object> beanEntity : beanMap.entrySet()) {
                //从BeanMap中获取bean类与Bean的实例
                Class<?> beanClass = beanEntity.getKey();
                Object beanInstance = beanEntity.getValue();
                //获取Bean类定义的所有成员变量(简称Bean Field)
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    //遍历BeanField
                    for (Field beanField : beanFields) {
                        //判断当前field是否带有Inject注解
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            //在BeanMap中获取BeanField对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                //通过反射初始化BeanField的值
                                ReflectionUtil.setFeild(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }

            }
        }


    }
}
