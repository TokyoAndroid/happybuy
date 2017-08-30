package com.zw.happybuy.http;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.ActivityLifecycleProvider;
import com.trello.rxlifecycle.components.RxActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Tok on 2017/8/24.
 */

public class HttpTransformer<R extends HttpResult<T>, T>  implements Observable.Transformer<R,T>{

    private ActivityLifecycleProvider provider;

    public HttpTransformer(ActivityLifecycleProvider provider){
        this.provider = provider;
    }

    @Override
    public Observable<T> call(Observable<R> rObservable) {

        return rObservable.flatMap(new Func1<R, Observable<T>>() {
            @Override
            public Observable<T> call(R r) {

                if(r.getRet() != 0){
                    return createData(r.getResult());
                }else {
                    return Observable.error(new ApiException("网络出错"));
                }

            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.<T>bindUntilEvent(ActivityEvent.PAUSE));
    }

    private Observable<T> createData(final T data){
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

}
