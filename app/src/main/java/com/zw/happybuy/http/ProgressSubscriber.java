package com.zw.happybuy.http;

import android.app.ProgressDialog;
import android.content.Context;

import com.orhanobut.logger.Logger;
import com.zw.happybuy.common.App;
import com.zw.happybuy.utils.NetworkUtils;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/8/25.
 */

public abstract class ProgressSubscriber<T> extends Subscriber<T>{

    ProgressDialogHandler mHandler;
    private ProgressDialogHandler.OnProgressDialogCannelListener listener;

    public ProgressSubscriber(Context context){
        listener = new ProgressDialogHandler.OnProgressDialogCannelListener() {
            @Override
            public void onCannelProgress() {
                if(!ProgressSubscriber.this.isUnsubscribed()){
                    ProgressSubscriber.this.unsubscribe();
                }
            }
        };

       mHandler = new ProgressDialogHandler(context, listener,true);
    }

    @Override
    public void onStart() {
        super.onStart();
        before();
    }



    @Override
    public void onCompleted() {
        dismissDialog();
    }

    @Override
    public void onError(Throwable e) {
        dismissDialog();

        if(!NetworkUtils.isConnected()){
            onFailed(ApiException.NETWORK_ERROR);
        } else if(e instanceof ApiException){
            onFailed(e.getMessage());
        } else {
            Logger.e(e,e.getMessage());
            onFailed(ApiException.UNKNOWN_ERROR);
        }
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }


    protected void showDialog(){
        if(mHandler != null){
            mHandler.sendEmptyMessage(ProgressDialogHandler.SHOW_PROGRESSDIALOG);
        }
    }

    private void dismissDialog(){
        if(mHandler != null){
            mHandler.sendEmptyMessage(ProgressDialogHandler.DISMISS_PROGRESSDIALOG);
            listener = null;
            mHandler = null;
        }
    }

    protected abstract void before();

    protected abstract void onSuccess(T t);

    protected abstract void onFailed(String message);

}
