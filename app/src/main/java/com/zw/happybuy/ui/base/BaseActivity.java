package com.zw.happybuy.ui.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zw.happybuy.R;
import com.zw.happybuy.common.App;
import com.zw.happybuy.common.AppManager;
import com.zw.happybuy.utils.StatusViewUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Tok on 2017/8/21.
 */

public abstract class BaseActivity extends RxAppCompatActivity{

    protected String TAG = getClass().getSimpleName();
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addToActivities(this);

        setContentView(getLayoutRes());

        StatusViewUtils.initStatusBar(this);

        unbinder = ButterKnife.bind(this);

        initPresenter();
        afterCreated(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        StatusViewUtils.removeStatusBar(this);
        super.onDestroy();
        unbinder.unbind();
        AppManager.getAppManager().finishActivity(this);

    }


    public abstract int getLayoutRes();


    public void openActivity(Class<?> activity,Bundle bundle){
        Intent intent = new Intent(this,activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public abstract void initPresenter();
    public abstract void afterCreated(@Nullable Bundle savedInstanceState);
}
