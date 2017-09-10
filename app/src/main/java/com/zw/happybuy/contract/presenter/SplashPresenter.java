package com.zw.happybuy.contract.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.baidu.location.Address;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.components.ActivityLifecycleProvider;
import com.zw.happybuy.common.App;
import com.zw.happybuy.common.AppManager;
import com.zw.happybuy.contract.SplashContract;
import com.zw.happybuy.listener.MyLocationListener;
import com.zw.happybuy.ui.MainActivity;
import com.zw.happybuy.ui.activity.GuideActivity;
import com.zw.happybuy.ui.base.RxLifeTransformer;
import com.zw.happybuy.utils.NetworkUtils;
import com.zw.happybuy.utils.ToastUtils;
import com.zw.happybuy.utils.rxbus.RxBus;
import com.zw.happybuy.utils.rxbus.RxBusSubscriber;
import com.zw.happybuy.utils.rxbus.RxBusTransformer;
import com.zw.happybuy.utils.SPUtils;
import com.zw.happybuy.utils.rxbus.event.RxBusLocationEvent;

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

    public LocationClient mLocationClient = null;
    public BDAbstractLocationListener mLocationListener = new MyLocationListener();

    public SplashPresenter(Context context, SplashContract.SplashView view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void goToActivity() {
        Observable.timer(3,TimeUnit.SECONDS,AndroidSchedulers.mainThread())
                .compose(new RxLifeTransformer<Long,Long>(view))
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {

                        boolean isFirstIn = SPUtils.getBoolean("isFirstIn",true);
                        //TODO 是否第一次进
                        Class clazz = true ? GuideActivity.class : MainActivity.class;
                        Intent intent = new Intent(context,clazz);
                        context.startActivity(intent);
                        SPUtils.saveBoolean("isFirstIn",false);
                        AppManager.getAppManager().finishCurrentActivity();

                    }
                });
    }

    @Override
    public void startLocation() {
        RxBus.getInstance().tObservable(RxBusLocationEvent.class)
                .compose(new RxBusTransformer<RxBusLocationEvent,RxBusLocationEvent>((ActivityLifecycleProvider) view))
                .subscribe(new RxBusSubscriber<RxBusLocationEvent>() {
                    @Override
                    protected void onReceiveEvent(RxBusLocationEvent event) {
                        BDLocation location = event.getLocation();
                        Address address = location.getAddress();
                        if(!TextUtils.isEmpty(address.city)){
                            SPUtils.saveString("location",address.city);
                            SPUtils.saveString("lat",location.getLatitude() + "");
                            SPUtils.saveString("lon",location.getLongitude() + "");
                            mLocationClient.stop();
                        }
                    }

                    @Override
                    protected void onFailed(Throwable e) {
                    }
                });


        mLocationClient = new LocationClient(App.getAppContext());
        mLocationClient.registerLocationListener(mLocationListener);
        initLocation();
        mLocationClient.start();
    }

    @Override
    public void stopLocation() {
        if(mLocationClient.isStarted()){
            mLocationClient.stop();
        }
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000); //请求间隔时间
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        mLocationClient.setLocOption(option);

        mLocationClient.setLocOption(option);
    }

    @Override
    public void start() {

    }
}
