package com.zw.happybuy.utils;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zw.happybuy.R;
import com.zw.happybuy.common.App;

/**
 * Created by Tok on 2017/8/30.
 */

public class SnackBarUtils {


    private SnackBarUtils(){

    }

    public static void showWithoutButtonLong(View view,String text){
        setMessageColor(Snackbar.make(view,text,Snackbar.LENGTH_LONG)).show();
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

    public static void showWithButtonAlways(View view, String text, String actionText, int snackBarHeight,View.OnClickListener listener){
        Snackbar snackbar = Snackbar.make(view,text,Snackbar.LENGTH_INDEFINITE)
                .setAction(actionText,listener)
                .setActionTextColor(ContextCompat.getColor(App.getAppContext(), R.color.colorPrimary));
        setMessageColor(snackbar);
        setHeight(snackbar,snackBarHeight).show();
    }

    public static void showWithoutButtonLong(View view, String text, int snackBarHeight){
        Snackbar snackbar = Snackbar.make(view,text,Snackbar.LENGTH_LONG);
        setMessageColor(snackbar);
        setHeight(snackbar,snackBarHeight).show();
    }

    public static Snackbar setMessageColor(Snackbar snackbar){
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(tv.getContext(),R.color.colorPrimary));
        return snackbar;
    }

    public static Snackbar setHeight(Snackbar snackbar,int height){
        View view = snackbar.getView();
        ViewGroup.LayoutParams params = snackbar.getView().getLayoutParams();
        if(params != null){
            params.height = height;
            snackbar.getView().setLayoutParams(params);
        }
        return snackbar;
    }
}
