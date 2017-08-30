package com.zw.happybuy.ui.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import com.zw.happybuy.common.AppManager;

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
        initStatusBar();

        unbinder = ButterKnife.bind(this);

        initPresenter();
        afterCreated(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        AppManager.getAppManager().finishActivity(this);

    }


    public abstract int getLayoutRes();

    private void initStatusBar(){
        //设置状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        //得到状态栏的高度
        int statusBarHeight = 0;
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            statusBarHeight = this.getResources().getDimensionPixelSize(resourceId);
        }

        //添加View到状态栏
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        ViewGroup decorView = (ViewGroup) findViewById(android.R.id.content);
        decorView.addView(view, params);

    }

    public void openActivity(Class<?> activity,Bundle bundle){
        Intent intent = new Intent(this,activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public abstract void initPresenter();
    public abstract void afterCreated(@Nullable Bundle savedInstanceState);
}
