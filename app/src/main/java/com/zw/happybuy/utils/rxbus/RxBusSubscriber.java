package com.zw.happybuy.utils.rxbus;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/8/26.
 */

public abstract class RxBusSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        onFailed(e);
    }

    @Override
    public void onNext(T t) {
        onReceiveEvent(t);
    }

    protected abstract void onReceiveEvent(T t);
    protected abstract void onFailed(Throwable e);
}
