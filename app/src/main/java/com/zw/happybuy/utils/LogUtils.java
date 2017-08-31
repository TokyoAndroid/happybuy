package com.zw.happybuy.utils;

import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by Tok on 2017/8/27.
 */

public class LogUtils {

    public static boolean isDebug = true;

    private LogUtils(){}

    public static void w (String tag,String msg){
        if(isDebug){
            init(tag);
            Logger.w(msg);
        }
    }

    public static void d (String tag, Object obj){
        if(isDebug){
            init(tag);
            Logger.d(obj);
        }
    }

    public static void i (String tag, String msg){
        if(isDebug){
            init(tag);
            Logger.i(msg);
        }
    }

    public static void e (String tag, String msg){
        if(isDebug){
            init(tag);
            Logger.e(msg);
        }
    }

    public static void e (String tag, Throwable e){
        if(isDebug){
            init(tag);
            Logger.e(e,e.getMessage());
        }
    }

    public static void json(String tag, String json){
        if(isDebug){
            init(tag);
            Logger.json(json);
        }
    }

    public static void xml(String tag,String xml){
        if(isDebug){
            init(tag);
            Logger.xml(xml);
        }
    }

    public static void wtf(String tag,String msg){
        if(isDebug){
            init(tag);
            Logger.wtf(tag,msg);
        }
    }

    private static void init(String tag){
        Logger.init(tag).logLevel(LogLevel.FULL);
    }
}
