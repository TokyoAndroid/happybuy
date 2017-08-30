package com.zw.happybuy.utils.rxbus;

/**
 * Created by Administrator on 2017/8/26.
 */

public class RxBusTestEvent {

    private String name;
    private String tag;

    public RxBusTestEvent(String name, String tag) {
        this.name = name;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
