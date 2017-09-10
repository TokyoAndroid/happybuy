package com.zw.happybuy.ui.base;

import android.app.Activity;
import android.content.Context;

import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.components.ActivityLifecycleProvider;
import com.trello.rxlifecycle.components.FragmentLifecycleProvider;

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
    private FragmentLifecycleProvider fragmentProvider;

    public RxLifeTransformer(BaseView view){
        if(view instanceof FragmentLifecycleProvider){
            this.fragmentProvider = (FragmentLifecycleProvider)view;
        } else if(view instanceof Activity){
            this.provider = (ActivityLifecycleProvider)view;
        }else {
            throw new RuntimeException("BaseView没有继承RxActivity或RxFragment");
        }
    }

    public RxLifeTransformer(Activity activity){
        if(activity instanceof ActivityLifecycleProvider){
            this.provider = (ActivityLifecycleProvider)activity;
        } else {
            throw new RuntimeException("BaseView没有继承RxActivity或RxFragment");
        }
    }

    @Override
    public Observable<T> call(Observable<R> rObservable) {
        if(provider != null){
            return rObservable.compose(provider.<T>bindUntilEvent(ActivityEvent.DESTROY));
        }else if(fragmentProvider != null){
            return rObservable.compose(fragmentProvider.<T>bindUntilEvent(FragmentEvent.PAUSE));
        }
        return null;
    }
}
