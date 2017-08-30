package com.zw.happybuy.contract.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.zw.happybuy.common.AppManager;
import com.zw.happybuy.contract.SplashContract;
import com.zw.happybuy.ui.MainActivity;
import com.zw.happybuy.ui.activity.GuideActivity;
import com.zw.happybuy.ui.base.RxLifeTransformer;
import com.zw.happybuy.utils.NetworkUtils;
import com.zw.happybuy.utils.rxbus.SPUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Tok on 2017/8/21.
 */

public class SplashPresenter implements SplashContract.SplashPresenter {

    private SplashContract.SplashView view;
    private Context context;

    public SplashPresenter(Context context, SplashContract.SplashView view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void checkAppVersion() {
        if(NetworkUtils.isWifiConnected(context)){
            Observable.timer(3, TimeUnit.SECONDS)
                    .compose(new RxLifeTransformer<Long,Long>(view))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            view.showIsUpdateDialog();
                        }
                    });
        } else {
            Observable.timer(3, TimeUnit.SECONDS)
                    .compose(new RxLifeTransformer<Long,Long>(view))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            goToActivity();
                        }
                    });
        }
    }

    @Override
    public void goToActivity() {
        boolean isFirstIn = SPUtils.getBoolean("isFirstIn",true);
        Class clazz = true ? GuideActivity.class : MainActivity.class;
        Intent intent = new Intent(context,clazz);
        context.startActivity(intent);
        SPUtils.saveBoolean("isFirstIn",false);
        AppManager.getAppManager().finishActivity((Activity) view);
    }

    @Override
    public void downloadApp() {

    }

    @Override
    public void installApp() {

    }

    @Override
    public void start() {

    }
}
