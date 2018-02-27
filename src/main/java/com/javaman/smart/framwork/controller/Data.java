package com.javaman.smart.framwork.controller;

/**
 * @author pengzhe
 * @date 2018/2/27 10:55
 * @description 返回数据对象
 */

public class Data {

    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }
}
