package com.zw.happybuy.common;

import android.app.Application;
import android.content.Context;


/**
 * Created by Tok on 2017/8/21.
 */

public class App extends Application {

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

    }

    public static Context getAppContext(){
        return mInstance;
    }
}
