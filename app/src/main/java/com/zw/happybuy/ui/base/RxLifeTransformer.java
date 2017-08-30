package com.zw.happybuy.ui.base;

import android.content.Context;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.ActivityLifecycleProvider;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Tok on 2017/8/27.
 *
 * 用来绑定生命周期
 */

public class RxLifeTransformer<R extends T,T> implements Observable.Transformer<R,T>{

    private ActivityLifecycleProvider provider;

    public RxLifeTransformer(BaseView view){
        if(view instanceof ActivityLifecycleProvider){
            this.provider = (ActivityLifecycleProvider)view;
        } else {
            throw new RuntimeException("BaseView没有继承RxActivity或RxFragment");
        }
    }

    @Override
    public Observable<T> call(Observable<R> rObservable) {
        return rObservable.compose(provider.<T>bindUntilEvent(ActivityEvent.PAUSE));
    }
}
