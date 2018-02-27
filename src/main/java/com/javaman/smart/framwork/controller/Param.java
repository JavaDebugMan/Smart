package com.javaman.smart.framwork.controller;

import com.javaman.smart.framwork.util.CastUtil;

import java.util.Map;

/**
 * @author pengzhe
 * @date 2018/2/27 10:49
 * @description 请求参数对象
 */

public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 根据参数名获取long型参数值
     *
     * @param name
     * @return
     */
    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     * 获取所有字段信息
     *
     * @return
     */
    public Map<String, Object> getMap() {
        return paramMap;
    }
}
