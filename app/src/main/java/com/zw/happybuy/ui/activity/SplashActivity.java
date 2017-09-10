package com.zw.happybuy.ui.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.orhanobut.logger.Logger;
import com.zw.happybuy.R;
import com.zw.happybuy.common.AppManager;
import com.zw.happybuy.contract.SplashContract;
import com.zw.happybuy.contract.presenter.SplashPresenter;
import com.zw.happybuy.listener.MyLocationListener;
import com.zw.happybuy.ui.base.BaseActivity;
import com.zw.happybuy.utils.LogUtils;
import com.zw.happybuy.utils.StatusViewUtils;
import com.zw.happybuy.utils.ToastUtils;

import butterknife.BindView;

/**
 * Created by Tok on 2017/8/21.
 */

public class SplashActivity extends BaseActivity implements SplashContract.SplashView {


    @BindView(R.id.title)
    RelativeLayout title;

    private SplashContract.SplashPresenter presenter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    public void initPresenter() {
        presenter = new SplashPresenter(this, this);
    }

    @Override
    public void afterCreated(@Nullable Bundle savedInstanceState) {
        startAnimation();
        presenter.startLocation();
        presenter.goToActivity();
    }

    @Override
    public void startAnimation() {
        AnimationSet set = new AnimationSet(true);

        Animation alphaAnimation = new AlphaAnimation(0, 1.0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);
        Animation scaleAnimation = new ScaleAnimation(0.5f,1.0f,0.5f,1.0f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);

        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);

        title.startAnimation(set);
    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void setPresenter(SplashContract.SplashPresenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

}
