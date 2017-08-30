package com.zw.happybuy.utils.rxbus;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.ActivityLifecycleProvider;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Tok on 2017/8/26.
 */

public class RxBusTransformer<R extends T,T> implements Observable.Transformer<R,T> {

    private ActivityLifecycleProvider provider;

    public RxBusTransformer(ActivityLifecycleProvider provider) {
        this.provider = provider;
    }

    @Override
    public Observable<T> call(Observable<R> rObservable) {
        return rObservable.compose(provider.<T>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
