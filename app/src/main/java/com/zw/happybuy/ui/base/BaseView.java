package com.zw.happybuy.ui.base;


/**
 * Created by Tok on 2017/8/21.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void initPresenter();

}
