package com.zw.happybuy.bean;

/**
 * Created by Administrator on 2017/8/31.
 */

public class HomeGridViewBean {
    private int resId;

    private String title;

    public int getResId() {
        return resId;
    }

    public HomeGridViewBean(int resId, String title) {
        this.resId = resId;
        this.title = title;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
