package com.zw.happybuy.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.zw.happybuy.R;
import com.zw.happybuy.common.App;

/**
 * Created by Tok on 2017/8/27.
 */

public class ToastUtils {

    private ToastUtils(){}

    private static Toast mToast;

    public static void showLong(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getAppContext(), text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    public static void showShort(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getAppContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

}
