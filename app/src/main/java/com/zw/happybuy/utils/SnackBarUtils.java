package com.zw.happybuy.utils;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.zw.happybuy.R;
import com.zw.happybuy.common.App;

/**
 * Created by Tok on 2017/8/30.
 */

public class SnackBarUtils {

    private SnackBarUtils(){}

    public static void showWithoutButtonLong(View view,String text){
        Snackbar.make(view,text,Snackbar.LENGTH_LONG)
                .show();
    }

    public static void showWithoutButtonShort(View view,String text){
        Snackbar.make(view,text,Snackbar.LENGTH_SHORT)
                .show();
    }

    public static void showWithButtonLong(View view, String text, String actionText, View.OnClickListener listener){
        Snackbar.make(view,text,Snackbar.LENGTH_LONG)
                .setAction(actionText,listener)
                .setActionTextColor(ContextCompat.getColor(App.getAppContext(), R.color.colorPrimary))
                .show();
    }

    public static void showWithButtonAlways(View view, String text, String actionText, View.OnClickListener listener){
        Snackbar.make(view,text,Snackbar.LENGTH_INDEFINITE)
                .setAction(actionText,listener)
                .setActionTextColor(ContextCompat.getColor(App.getAppContext(), R.color.colorPrimary))
                .show();
    }
}
