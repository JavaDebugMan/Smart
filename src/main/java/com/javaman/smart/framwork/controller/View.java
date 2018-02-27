package com.javaman.smart.framwork.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pengzhe
 * @date 2018/2/27 10:52
 * @description 返回视图对象
 */

public class View {

    /**
     * 视图路径
     */
    private String path;

    /**
     * 数据模型
     */
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<String, Object>();
    }

    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
