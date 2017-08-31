package com.zw.happybuy.common;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;
import com.zw.happybuy.utils.LogUtils;


/**
 * Created by Tok on 2017/8/21.
 */

public class App extends Application {

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        //初始化Bugly
        CrashReport.initCrashReport(getAppContext(),"420e34c7ae", LogUtils.isDebug);
        //初始化LeakCanary
        setupLeakCanary();

    }

    protected RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }
}
