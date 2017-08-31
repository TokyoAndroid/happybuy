package com.zw.happybuy.http;

import android.app.ProgressDialog;
import android.content.Context;

import com.orhanobut.logger.Logger;
import com.zw.happybuy.utils.NetworkUtils;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/8/25.
 */

public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressDialogHandler.OnProgressDialogCannelListener {

    ProgressDialogHandler mHandler;

    public ProgressSubscriber(Context context){
       mHandler = new ProgressDialogHandler(context,this,true);
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
            mHandler = null;
        }
    }

    protected abstract void before();

    protected abstract void onSuccess(T t);

    protected abstract void onFailed(String message);



    @Override
    public void onCannelProgress() {
        if(!this.isUnsubscribed()){
            this.unsubscribe();
        }
    }
}
