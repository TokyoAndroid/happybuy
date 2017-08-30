package com.zw.happybuy.utils.rxbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Tok on 2017/8/26.
 */

public class RxBus {

    private static volatile RxBus mInstance;
    private final Subject bus;

    private RxBus(){
        bus = new SerializedSubject(PublishSubject.create());
    }

    public static RxBus getInstance(){
        return RxBusInnerInstance.INSTANCE;
    }

    private static class RxBusInnerInstance{
        private static final RxBus INSTANCE = new RxBus();
    }

    //发送消息
    public void post(Object obj){
        bus.onNext(obj);
    }

    //接收消息
    public <T>Observable<T> tObservable(Class<T> clazz){
        return bus.ofType(clazz);
    }
}
