package com.zw.happybuy.utils.rxbus;

import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * Created by Tok on 2017/8/31.
 */

public class RxBindingUtils  {

    private RxBindingUtils(){}

    //2秒内只响应一次点击事件
    public static void click(View view,Action1<Void> aciton){
        RxView.clicks(view)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(aciton);
    }
}
