package com.zw.happybuy.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;
import com.zw.happybuy.R;
import com.zw.happybuy.common.App;

/**
 * Created by Tok on 2017/9/9.
 */

public class StatusViewUtils {

    @android.support.annotation.IdRes
    static int statusBarId = 12345;

    private  StatusViewUtils(){}

    public static void initStatusBar(Activity activity){
        //设置状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        //得到状态栏的高度
        int statusBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }

        //添加View到状态栏
        View statusView = new View(activity);
        statusView.setId(statusBarId);
        statusView.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        ViewGroup decorView = (ViewGroup) activity.findViewById(android.R.id.content);
        decorView.addView(statusView, params);

    }

    public static void removeStatusBar(Activity activity){
        ViewGroup decorView = (ViewGroup) activity.findViewById(android.R.id.content);
        View statusView = decorView.findViewById(statusBarId);
        if(statusView != null){
            Logger.i("statusView != null ");
            decorView.removeView(statusView);
            statusView = null;
        }
    }
}
